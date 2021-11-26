package partie;
import java.util.ArrayList;
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
	private ArrayList<Carte> main;
	private ArrayList<EffetAvantTour> effetsAvantTour;
	public Joueur(String nom) {
		this.nomJoueur=nom;
		this.main=new ArrayList<Carte>();
		this.score=0;
		this.revelee=false;
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
	
	public void setScore(int score) {
		this.score=score;
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
			this.effetsAvantTour.remove(e);
		}
		
		
		System.out.println("What do you want to do?");
		//check s'il a des cartes jouables
		
		if(this.getPlayableCards().size()==0) {
			System.out.println("Because you have no playable cards you have to accuse someone!");
			accuser();
		}
		else {
			System.out.println("Type A to accuse another player of being a Witch");
			System.out.println("Type R to reveal a Rumour card from your hand and play it face up in front of yourself, resolving it's Hunt! Effect.");
			String resultat = Partie.getInstance().chooseBetween2Options("A", "R");
			if(resultat.equals("A")) {
				accuser();
			}
			else if (resultat.equals("R")) {
				try {
					playHuntCard();
				} catch (NoCardsToChooseFromException e) {
					System.out.println(e);
					playTurn();
				}
			}
		}
		
	
	}

	public Carte choisirCarteAJouer() throws NoCardsToChooseFromException {
		ArrayList<Carte> cartes = this.getPlayableCards();
		if(cartes.size()!=0) {
			Iterator<Carte> it = cartes.iterator();
			int index=0;
			//lors de l'activation de l'effet : vérif playable if you have rumour card etc...
			while(it.hasNext()) {
				index++;
				System.out.println(index+" : \n" + it.next().toString());
			}
			int number = Partie.getInstance().askNumber(1, index);
			return cartes.get(number-1);
		}
		else {
			throw new NoCardsToChooseFromException(this.toString() + " has no cards to choose from!");
		}
	}
	private void playHuntCard() throws NoCardsToChooseFromException {
		System.out.println("Choose a card to play!");
		Carte c = choisirCarteAJouer();
		c.activerEffetHunt(this);
	}
		

	private void accuser() {
		System.out.println("Choose a player to accuse!");
		Joueur jAccusee = this.choisirJoueur();
		System.out.println(this.toString() + " accused " + jAccusee.toString() + " of being a Witch! \n");
		jAccusee.etreAccuse(this);
	}

	public void addToScore(int points) {
		this.score+=points;
	}
	
	private void etreAccuse(Joueur accusateur) {
		System.out.println("What'll " + this.toString() + " do to answer this accusation? \n");
		if(this.getPlayableCards().size()!=0) {
			System.out.println("Type I to reveal your Identity Card");
			System.out.println("Type R to reveal a Rumour card from your hand and play it face up in front of yourself, resolving it's Hunt? Effect.");
			String resultat = Partie.getInstance().chooseBetween2Options("I", "R");
			if(resultat.equals("I")) {
				this.revelerIdentite(accusateur);
			}
			else if(resultat.equals("R")) {
				try {
					this.playWitchCard(accusateur);
				} catch (NoCardsToChooseFromException e) {
					System.out.println(e);
					etreAccuse(accusateur);
				}
			}
		}
		else {
			System.out.println("Because you have no playable cards you have reveal your Identity Card!");
			this.revelerIdentite(accusateur);
		}
	}
	
	private void playWitchCard(Joueur accusateur) throws NoCardsToChooseFromException {
		System.out.println("Choose a card to play!");
		Carte c = choisirCarteAJouer();
		c.activerEffetWitch(this,accusateur);
	}



	private void revelerIdentite(Joueur accusateur) {
		this.revelee=true;
		if(this.estSorciere) {
			System.out.println(this.toString() + " was a Witch! \n");
			Partie.getInstance().eliminerJoueur(this);
			accusateur.addToScore(1);
		}
		else {
			System.out.println(this.toString() + " was a Villager! \n");
			Partie.getInstance().getListeJoueurs().movePlayerFirst(this);
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
		
		String resultat = Partie.getInstance().chooseBetween2Options("W", "V");
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
		int number = Partie.getInstance().askNumber(1, index);
		return joueurs.get(number-1);
	}
	
	public Joueur choisirJoueurNonRevelee() {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(this);
		Iterator<Joueur> it = joueurs.iterator();
		int index=0;
		while(it.hasNext()) {
			index++;
			System.out.println(index + " : " +it.next().toString());
		}
		int number = Partie.getInstance().askNumber(1, index);
		return joueurs.get(number-1);
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
}
