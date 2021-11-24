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
		//check si le joeur n'est pas éliminé...
		Scanner sc = new Scanner(System.in);
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
		int index=0;
		Scanner sc = new Scanner(System.in);
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
		Scanner sc = new Scanner(System.in);
		int index=0;
		while(it.hasNext()) {
			index++;
			System.out.println(index + " : " +it.next().toString());
		}
		int number = Partie.getInstance().askNumber(1, index);
		sc.close();
		Joueur jAccusée = joueurs.get(number);
		System.out.println(this.toString() + "accused " + jAccusée.toString() + " of being a Witch!");
		jAccusée.etreAccuse(this);
	}


	private void etreAccuse(Joueur j) {
		//if()
		System.out.println("What'll + " + this.toString() + " do to answer this accusation?");
		
	}



	public boolean estEliminee() {
		return this.elimine;
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
		Scanner sc = Partie.getInstance().getScanner();
		boolean roleChosen = false;
		System.out.println("\n \n" +"If you want to play as a Witch, type W.\nIf you want to play as a Villager, type V. ");
		while(!roleChosen) {
			String s = sc.nextLine();
			if(s.equals("W")|s.equals("w")) {
				System.out.println("You're now a witch!");
				this.setEstSorciere(true);
				roleChosen = true;
			}
			else if(s.equals("V")|s.equals("v")) {
				System.out.println("You're now a villager");
				this.setEstSorciere(false);
				roleChosen = true;
			}
			else {
				System.out.println("Please type either W or V");
				roleChosen = false;
			}
	}
	}
	public String toString() {
		return this.nomJoueur;
	}
	
}
