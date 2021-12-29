package partie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;

import Vue.AskNumberCLI;
import Vue.CartesCLI;
import Vue.CartesGUI;
import Vue.ChooseBetween2OptionsGUI;
import effets.EffetAvantTour;

public class Joueur extends Observable{
	private String nomJoueur;
	private Boolean estSorciere;
	private int score;
	private boolean elimine;
	private boolean revelee;
	private Joueur untargetablePlayer;
	private ArrayList<Carte> main;
	private ArrayList<EffetAvantTour> effetsAvantTour;
	
	private Carte chosenCard;
	private Action action;
	public Joueur(String nom) {
		this.nomJoueur=nom;
		this.main=new ArrayList<Carte>();
		this.score=0;
		this.elimine=false;
		this.revelee=false;
		this.untargetablePlayer=null;
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
			System.out.println(it.next().affichageCarte());
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
		
		
		System.out.println("What do you want to do?\n");
		ArrayList<Joueur> joueurs = new ArrayList<>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(this);
		if(untargetablePlayer!=null) {
			joueurs.remove(untargetablePlayer);
			System.out.println("Because of their last card, you can't accuse " + untargetablePlayer.toString());
		}
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
	
	protected void accuseOrUseCard() throws NoPlayersToChooseFromException, NoCardsToChooseFromException  {
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
	
	protected void revealIdentityOrUseCard(Joueur accusateur) throws NoCardsToChooseFromException  {
		String resultat = this.chooseBetween2Options("I", "R");
		if(resultat.equals("I")) {
			System.out.println(this.toString() + "decided to reveal their identity!");
			this.revelerIdentite(accusateur);
		}
		else if(resultat.equals("R")) {
				System.out.println(this.toString() + "decided to reveal a Rumour Card!");
				this.playWitchCard(accusateur);
		}
	}
	public Carte choisirCarteAJouer() throws NoCardsToChooseFromException {
		return this.choisirCarte(this.getPlayableCards());
	}
	
	public int askNumber(int min, int max) {
		return 0;//A MODIF;
	}
	public void playHuntCard() throws NoCardsToChooseFromException {
		System.out.println("Choose a card to play!\n");
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
			if(!this.isABot()) {
				System.out.println("Type I to reveal your Identity Card");
				System.out.println("Type R to reveal a Rumour card from your hand and play it face up in front of yourself, resolving it's Hunt? Effect.");
			}
			try {
				this.revealIdentityOrUseCard(accusateur);
			} catch (NoCardsToChooseFromException e) {
				System.out.println(e);
			}
		}
		else {
			System.out.println("Because you have no playable cards you have to reveal your Identity Card!");
			this.revelerIdentite(accusateur);
		}
	}
	
	protected void playWitchCard(Joueur accusateur) throws NoCardsToChooseFromException {
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
			System.out.println(accusateur.toString() + " gets 1 point!");
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
	public Boolean estSorciere() {
		return this.estSorciere;
	}
	public void setEstSorciere(boolean role) {
		synchronized(this) {
			this.estSorciere=role;
			this.notifyAll();
		}

		
	}
	public void setEstRevele() {
		this.revelee=true;
	}
	public boolean isABot() {
		return false;
	}
	public ArrayList<Carte> getCards() {
		return this.main;
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
			Joueur j   = it.next();
			if(!this.isABot()) {
				System.out.println(index + " : " +j.toString());
			}
		}
		int number = this.askNumber(1, index);
		return joueurs.get(number-1);
	}
	
	
	
	public String chooseBetween2Options(String a, String b) {
		this.action=null;
		ChooseBetween2OptionsGUI GUI = new ChooseBetween2OptionsGUI(a,b,this);
		synchronized(this) {
		while(this.action==null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(this.action==Action.OPTION1) {
			return a;
		}
		else {
			return b;
		}
		}
		/*Scanner sc = Partie.getInstance().getScanner();
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
		return null;*/
	}
	
	public void reset() {
		this.main=new ArrayList<Carte>();
		this.score=0;
		this.revelee=false;
		this.elimine=false;
		this.untargetablePlayer=null;
		this.untargetablePlayer = null;
		effetsAvantTour = new ArrayList<EffetAvantTour>();
	}
	public Joueur choisirJoueurAccusation() throws NoPlayersToChooseFromException {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(this);
		if(untargetablePlayer!=null) {
			joueurs.remove(untargetablePlayer);
		}
		return this.choisirJoueur(joueurs);
	}
	public Joueur choisirJoueur(ArrayList<Joueur> joueurs) throws NoPlayersToChooseFromException {
		if(joueurs.size()!=0) {
			Iterator<Joueur> it = joueurs.iterator();
			int index=0;
			while(it.hasNext()) {
				index++;
				Joueur j = it.next();
				if(!this.isABot()) {
					System.out.println(index + " : " +j.toString());
				}
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


	public void setChosenCard(Carte c) {
		synchronized(this) {
			this.chosenCard=c;
			this.notifyAll();
		}
	}
	public Carte getChosenCard() {
		return this.chosenCard;
		
	}
	public Carte choisirCarte(ArrayList<Carte> cartes) throws NoCardsToChooseFromException {
		chosenCard = null;
		System.out.println("choix");
		if(cartes.size()!=0) {
			CartesGUI vue = new CartesGUI(cartes,this);
			CartesCLI vueCLI = new CartesCLI(this,cartes);
			synchronized(this) {
				while(chosenCard==null) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}

			System.out.println(chosenCard.getNomCarte());
			return chosenCard;
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

	public void setUntargetablePlayer(Joueur j) {
		this.untargetablePlayer=j;
	}



	public void updatePlayer() {
		this.setChanged();
		this.notifyObservers();
		
	}



	public void setAction(Action action) {
		synchronized(this) {
			this.action=action;
			this.notifyAll();
		}
	}






}
