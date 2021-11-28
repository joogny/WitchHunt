package partie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import effets.EffetAvantTour;

public class Joueur {
	private String nomJoueur;
	private boolean estSorciere;
	private int score;
	private boolean elimine;
	private boolean revelee;
	private HashSet<Joueur> untargetablePlayer;
	private ArrayList<Carte> main;
	private ArrayList<EffetAvantTour> effetsAvantTour;
	
	public Joueur(String nom) {
		this.nomJoueur=nom;
		this.main=new ArrayList<Carte>();
		this.score=0;
		this.elimine=false;
		this.revelee=false;
		this.untargetablePlayer = new HashSet<>();
		effetsAvantTour = new ArrayList<EffetAvantTour>();
	}
	
	
	
	public void ajouterCarte(Carte c) {
		if(!main.contains(c)) {
			main.add(c);
		}
	}
	
	public String getNomJoueur() {
		return nomJoueur;
	}
	
	public ArrayList<Carte> getMain() {
		return main;
	}
	public ArrayList<Carte> getPlayableCards() {
		ArrayList<Carte> playableCards = new ArrayList<Carte>();
		Iterator<Carte> it = main.iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.estJouable()) {
				playableCards.add(c);
			}
		}
		return playableCards;
	}
	public ArrayList<Carte> getPlayableHuntCards() {
		ArrayList<Carte> playableCards = new ArrayList<Carte>();
		Iterator<Carte> it = main.iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.estJouableHunt(this)) {
				playableCards.add(c);
			}
		}
		return playableCards;
	}
	public ArrayList<Carte> getPlayableWitchCards() {
		ArrayList<Carte> playableCards = new ArrayList<Carte>();
		Iterator<Carte> it = main.iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.estJouableWitch(this)) {
				playableCards.add(c);
			}
		}
		return playableCards;
	}
	public void setScore(int score) {
		if(score<0) {
			this.score=0;
		}
		else {
			this.score=score;
		}
	}
	
	public void defausseCarte(Carte c) {
		main.remove(c);
		c.setDefausse(true);
	}
	public ArrayList<Carte> getRevealedCards() {
		ArrayList<Carte> playableCards = new ArrayList<Carte>();
		Iterator<Carte> it = main.iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.isRevelee()&& !c.isDefausse()) {
				playableCards.add(c);
			}
		}
		return playableCards;
	}

	public void displayHand() {
		Iterator<Carte> it = main.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		
	}
	
	public void playTurn() {
		
		System.out.println("It's " + this.toString()+"'s turn!\n");
		
		//check Effets de départ...
		Iterator<EffetAvantTour> itAvantTour = this.effetsAvantTour.iterator();
		while(itAvantTour.hasNext()) {
			EffetAvantTour e = itAvantTour.next();
			e.lancerEffet(this);
			itAvantTour.remove();
		}
		
		
		System.out.println("What do you want to do?");
		ArrayList<Joueur> joueurs = new ArrayList<>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(this);
		if(joueurs.size()==0 && this.getPlayableHuntCards().size()==0) {
			System.out.println("You don't have any cards to play and you can't accuse anyone so you have to skip your turn!");
			System.exit(score);
		}
		else if(joueurs.size()==0) {
			System.out.println("There isn't anyone left to accuse so you have to play a card");
			try {
				playHuntCard();
			} catch (NoCardsToChooseFromException e) {
				System.out.println("ERROR");
			}
		}
		else if(this.getPlayableHuntCards().size()==0) {
			System.out.println("Because you have no playable cards you have to accuse someone!");
			
			try {
				accuser();
			} catch (NoPlayersToChooseFromException e) {
				System.out.println("ERROR");
			}
		}
		else {
			if(!this.isABot()) {
				System.out.println("Type A to accuse another player of being a Witch");
				System.out.println("Type R to reveal a Rumour card from your hand and play it face up in front of yourself, resolving it's Hunt! Effect.");
				
			}
			try {
				accuseOrUseCard();
			} catch (NoPlayersToChooseFromException e) {
				System.out.println("You can't accuse anyone that's still playing!\nYou have to play a HuntCard");
				playTurn();
			} catch (NoCardsToChooseFromException e) {
				System.out.println(e);
				playTurn();
			}
		}
		
	
	}
	public void accuseOrUseCard() throws NoPlayersToChooseFromException, NoCardsToChooseFromException  {
		String resultat = this.chooseBetween2Options("A", "R");
		if(resultat.equals("A")) {
			System.out.println(this.toString() + " decided to accuse someone");
				accuser();
		}
		else if (resultat.equals("R")) {
				System.out.println(this.toString() + " decided to reveal a Rumour Card");
				playHuntCard();
		}
	}
	public Carte choisirCarteAJouer() throws NoCardsToChooseFromException {
		return this.choisirCarte(this.getPlayableCards());
	}
	
	public int askNumber(int min, int max) {
		return Partie.getInstance().askNumber(min, max);
	}
	public void playHuntCard() throws NoCardsToChooseFromException {
		System.out.println("Choose a card to play!");
		Carte c = choisirCarte(this.getPlayableHuntCards());
		c.activerEffetHunt(this);
		c.setRevelee(true);
	}
		

	public void accuser() throws NoPlayersToChooseFromException{
		System.out.println("Choose a player to accuse!");
		Joueur jAccusee;
		jAccusee = this.choisirJoueurAccusation();
		System.out.println(this.toString() + " accused " + jAccusee.toString() + " of being a Witch! \n");
		jAccusee.etreAccuse(this);
	}

	public void addToScore(int points) {
		this.score+=points;
	}
	
	private void etreAccuse(Joueur accusateur) {
		System.out.println("What'll " + this.toString() + " do to answer this accusation? \n");
		if(this.getPlayableWitchCards().size()!=0) {
			if(!this.estSorciere) {
				System.out.println("Type I to reveal your Identity Card");
				System.out.println("Type R to reveal a Rumour card from your hand and play it face up in front of yourself, resolving it's Hunt? Effect.");
			}
			String resultat = this.chooseBetween2Options("I", "R");
			if(resultat.equals("I")) {
				System.out.println(this.toString() + "decided to reveal their identity!");
				this.revelerIdentite(accusateur);
			}
			else if(resultat.equals("R")) {
				try {
					System.out.println(this.toString() + "decided to reveal a Rumour Card!");
					this.playWitchCard(accusateur);
				} catch (NoCardsToChooseFromException e) {
					System.out.println(e);
					System.out.println(e);
					etreAccuse(accusateur);
				}
			}
		}
		else {
			System.out.println("Because you have no playable cards you have to reveal your Identity Card!");
			this.revelerIdentite(accusateur);
		}
	}
	
	private void playWitchCard(Joueur accusateur) throws NoCardsToChooseFromException {
		System.out.println("Choose a card to play!");
		Carte c = choisirCarte(this.getPlayableWitchCards());
		c.activerEffetWitch(this,accusateur);
		c.setRevelee(true);
	}



	public void revelerIdentite(Joueur accusateur) {
		this.revelee=true;
		if(this.estSorciere) {
			System.out.println(this.toString() + " was a Witch! \n");
			Partie.getInstance().eliminerJoueur(this);
			accusateur.addToScore(1);
			System.out.println(accusateur.toString() + "gets 1 point!");
		}
		else {
			System.out.println(this.toString() + " was a Villager! \n");
			Partie.getInstance().getListeJoueurs().movePlayerFirst(this);
		}
	}

	public void revelerIdentite() {
		this.revelee=true;
		if(this.estSorciere) {
			System.out.println(this.toString() + " was a Witch! \n");
			Partie.getInstance().eliminerJoueur(this);
		}
		else {
			System.out.println(this.toString() + " was a Villager! \n");
		}
	}

	public boolean estEliminee() {
		return this.elimine;
	}
	public boolean estRevelee() {
		return this.revelee;
	}
	public int getScore() {
		return score;
	}
	public boolean estSorciere() {
		return this.estSorciere;
	}
	public void setEstSorciere(boolean role) {
		this.estSorciere=role;
	}
	public void setEstRevele() {
		this.revelee=true;
	}
	public boolean isABot() {
		return false;
	}


	public void discoverHand() {
		Scanner sc = Partie.getInstance().getScanner();
		System.out.println("\n \n"+ "Hello " + this.getNomJoueur() + ", if you're ready to look at your cards and that no one else is looking at the screen, press ENTER!");
		sc.nextLine();
		this.displayHand();
	}
	public void chooseIdentityCard() {
		System.out.println("\n \n" +"If you want to play as a Witch, type W.\nIf you want to play as a Villager, type V. ");
		
		String resultat = this.chooseBetween2Options("W", "V");
		if(resultat == "W") {
			System.out.println("You're now a witch!");
			this.setEstSorciere(true);
		}
		else if(resultat == "V") {
			System.out.println("You're now a villager");
			this.setEstSorciere(false);
		}
	}
	public String toString() {
		return this.nomJoueur;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Joueur) {
			Joueur j = (Joueur) o;
			if(!(j.nomJoueur==this.nomJoueur)) {
				return false;
			}
			if(!(j.main.size()==this.main.size())) {
				return false;
			}
			Iterator<Carte> it1 = this.main.iterator();
			Iterator<Carte> it2 = j.main.iterator();
			while(it1.hasNext()&&it2.hasNext()) {
				if(!it1.next().equals(it2.next()))  {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public Joueur choisirJoueur() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getListeJoueurs());
		joueurs.remove(this);
		Iterator<Joueur> it = joueurs.iterator();
		int index=0;
		while(it.hasNext()) {
			index++;
			System.out.println(index + " : " +it.next().toString());
		}
		int number = this.askNumber(1, index);
		return joueurs.get(number-1);
	}
	
	
	
	public String chooseBetween2Options(String a, String b) {
		boolean actionChosen=false;
		Scanner sc = Partie.getInstance().getScanner();
		String s = sc.next();
		if(s.equals(a)) {
			return a;
			}
		else if(s.equals(b)) {
			return b;	
		}
		else {
			System.out.println("Please type either "+ a+ " or " + b);
			chooseBetween2Options(a, b);
		}
		return null;
	}
	
	public void reset() {
		this.main=new ArrayList<Carte>();
		this.score=0;
		this.revelee=false;
		this.elimine=false;
		this.untargetablePlayer = new HashSet<>();
		effetsAvantTour = new ArrayList<EffetAvantTour>();
	}
	public Joueur choisirJoueurAccusation() throws NoPlayersToChooseFromException {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(this);
		return this.choisirJoueur(joueurs);
	}
	public Joueur choisirJoueur(ArrayList<Joueur> joueurs) throws NoPlayersToChooseFromException {
		if(joueurs.size()!=0) {
			Iterator<Joueur> it = joueurs.iterator();
			int index=0;
			while(it.hasNext()) {
				index++;
				System.out.println(index + " : " +it.next().toString());
			}
			int number = this.askNumber(1, index);
			return joueurs.get(number-1);
		}
		else {
			throw new NoPlayersToChooseFromException("There are no unrevealed players to choose from");
		}

	}
	public Joueur choisirJoueurNonRevelee() throws NoPlayersToChooseFromException {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(this);
		return choisirJoueur(joueurs);
	}



	public void ajouterEffetDebutTour(EffetAvantTour effet) {
		// TODO Auto-generated method stub
		this.effetsAvantTour.add(effet);
	}



	public void secretlyLookAtIdentity(Joueur joueurVisé) {
		System.out.println("\n \n"+ "Hello " + this.getNomJoueur() + ", if you're ready to look at "+ joueurVisé.getNomJoueur() + "'s identity, press ENTER!");
		Scanner sc = Partie.getInstance().getScanner();
		sc.nextLine();
		if(joueurVisé.estSorciere) {
			System.out.println("They were a Witch!");
		}
		else {
			System.out.println("They were a Villager!");
		}
		System.out.println("Press ENTER to hide the screen!");
		sc.nextLine();
	}



	public Carte choisirCarte(ArrayList<Carte> cartes) throws NoCardsToChooseFromException {
		if(cartes.size()!=0) {
			Iterator<Carte> it = cartes.iterator();
			int index=0;
			//lors de l'activation de l'effet : vérif playable if you have rumour card etc...
			while(it.hasNext()) {
				index++;
				System.out.println(index+" : \n" + it.next().toString());
			}
			int number = this.askNumber(1, index);
			System.out.println(this.toString() + " chose " + cartes.get(number-1));
			return cartes.get(number-1);
		}
		else {
			throw new NoCardsToChooseFromException(this.toString() + " has no cards to choose from!");
		}
	}



	public void removeCardFromHand(Carte c) {
		main.remove(c);
	}



	public void addCardToHand(Carte c) {
		main.add(c);
	}



	public void chooseRevealOrIdentity(Joueur accusateur) {
		if(!this.revelee&&!(this.getPlayableCards().size()==0)) {
			System.out.println("Do you want to discard a card or reveal your identity?");
			System.out.println("Type D to discard a card");
			System.out.println("Type R to reveal your identity");
			String s = this.chooseBetween2Options("D","R");
			if(s=="D") {
				Carte c;
				try {
					c = this.choisirCarteAJouer();
					this.defausseCarte(c);
					Partie.getInstance().getListeJoueurs().movePlayerFirst(this);
				} catch (NoCardsToChooseFromException e) {
					System.out.println(e);
				}
			}
			else {
				if(this.estSorciere) {
					System.out.println(this.toString() + "was a Witch!");
					Partie.getInstance().getListeJoueurs().eliminerJoueur(this);
					this.revelee=true;
					accusateur.addToScore(1);
					Partie.getInstance().getListeJoueurs().movePlayerFirst(accusateur);
				}
				else {
					this.revelee=true;
					
					accusateur.setScore(accusateur.score-1);
					Partie.getInstance().getListeJoueurs().movePlayerFirst(this);
				}
			}
		}
		else if(this.revelee&&!(this.getPlayableWitchCards().size()==0)) {
			try {
				System.out.println("Your identity is already revealed so you have to discard a card!");
				Carte c;
				c = this.choisirCarteAJouer();
				this.defausseCarte(c);
			} catch (NoCardsToChooseFromException e) {
				System.out.println("You have no cards to discard and your identity is already revealed so you don't have to do anything!");
			}
		}
		else {
			System.out.println("You have no cards to discard and your identity is already revealed so you don't have to do anything!");
		}
		
	}



	public void addUntargetablePlayer(Joueur j) {
		untargetablePlayer.add(j);
	}
}
