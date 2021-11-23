package partie;
import java.util.ArrayList;
import java.util.Iterator;
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
			if(!c.isDefausse()) {
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
			if(c.isDefausse()) {
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
	
	private void playTurn() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("What do you want to do?");
		//check s'il a des cartes jouables
		if(this.getPlayableCards().size()==0) {
			System.out.println("Because you have no playable cards you have to accuse someone!");
			
		}
		else {
			System.out.println("Type A to accuse another player of being a Witch");
			System.out.println("Type R to reveal a Rumour card from your hand and play it face up in front of yourself, resolving it's Hunt! Effect.");
			String s = sc.next();
			boolean turnActionChosen=false;
			while(!turnActionChosen) {
				if(s=="A"||s=="a") {
					turnActionChosen=true;
					accuser();
				}
				else if(s=="R"||s=="r") {
					turnActionChosen=true;	
					playHuntCard();
				}
				else {
					System.out.println("Please type either A or R");
				}
			}
		}
		
		
		sc.close();
	}

	private void playHuntCard() {
		// TODO Auto-generated method stub
		System.out.println("Choose a card to play!");
	}



	private void accuser() {
		// TODO Auto-generated method stub
		System.out.println("Choose a player to accuse!");

	}



	public void setEstSorciere(boolean role) {
		this.estSorciere=role;
	}
	public boolean isABot() {
		return false;
	}
	
	
}
