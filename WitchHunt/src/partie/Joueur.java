package partie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Joueur {
	private String nomJoueur;
	private boolean estSorciere;
	private int score;
	private boolean elimine;
	private boolean revelee;
	private ArrayList<Carte> main;
	
	public Joueur(String nom) {
		this.nomJoueur=nom;
		this.main=new ArrayList<Carte>();
		this.score=0;
		this.revelee=false;
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
			if(!c.isDefausse() && !c.isRevelee()) {
				playableCards.add(c);
			}
		}
		return playableCards;
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
		
		Scanner sc = Partie.getInstance().getScanner();
		
		System.out.println("It's " + this.toString()+"'s turn. Press ENTER to start!");
		sc.nextLine();
		
		//check Effets de départ...
		
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
				playHuntCard();
			}
		}
		
	
	}

	private void playHuntCard() {
		// TODO Auto-generated method stub
		int index=0;
		Scanner sc = Partie.getInstance().getScanner();
		System.out.println("Choose a card to play!");
		ArrayList<Carte> cartes = this.getPlayableCards();
		Iterator<Carte> it = cartes.iterator();
		while(it.hasNext()) {
			index++;
			System.out.println(index+" : \n" + it.next().toString());
		}
		System.out.println("Choose a card to play!");
		int number = Partie.getInstance().askNumber(1, index);
		
		Carte c = cartes.get(number);
		c.activerEffetHunt(this);
		//à finir
	}
		


	private void accuser() {
		// TODO Auto-generated method stub
		System.out.println("Choose a player to accuse!");
		LinkedList<Joueur> joueurs = Partie.getInstance().getListeJoueurs().getJoueursNonEliminées();
		Iterator<Joueur> it = joueurs.iterator();
		Scanner sc = Partie.getInstance().getScanner();
		int index=0;
		while(it.hasNext()) {
			index++;
			System.out.println(index + " : " +it.next().toString());
		}
		int number = Partie.getInstance().askNumber(1, index);
		Joueur jAccusée = joueurs.get(number);
		System.out.println(this.toString() + " accused " + jAccusée.toString() + " of being a Witch! \n");
		jAccusée.etreAccuse(this);
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
				this.playWitchCard(accusateur);
			}
		}
		else {
			System.out.println("Because you have no playable cards you have reveal your Identity Card!");
			this.revelerIdentite(accusateur);
		}
	}
	
	private void playWitchCard(Joueur accusateur) {
		//méthode à faire
		System.out.println("A FAIRE");
	}



	private void revelerIdentite(Joueur accusateur) {
		this.revelee=true;
		if(this.estSorciere) {
			System.out.println(this.toString() + " was a Witch!");
			Partie.getInstance().eliminerJoueur(this);
			accusateur.addToScore(1);
		}
		else {
			System.out.println(this.toString() + " was a Villager");
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
		if(resultat.equals("W")) {
			System.out.println("You're now a witch!");
			this.setEstSorciere(true);
		}
		else if(resultat.equals("V")) {
			System.out.println("You're now a villager");
			this.setEstSorciere(false);
		}
	}
	public String toString() {
		return this.nomJoueur;
	}
	
}
