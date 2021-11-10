import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class Partie {
	
	private static Partie instance;
	private ArrayList<Carte> cartes;
	private LinkedList<Joueur> joueurs;
	
	private static final int MAX_PLAYER_COUNT = 6;
	private static final int MIN_PLAYER_COUNT = 3;
	private Partie() {
	}
	
	public static Partie getInstance() {
		if(instance==null) {
			instance = new Partie();
		}
		return instance;
	}
	
	
	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	public LinkedList<Joueur> getJoueurs() {
		return joueurs;
	}

	public static int getMaxPlayerCount() {
		return MAX_PLAYER_COUNT;
	}

	private void demarrerPartie() {
		Scanner sc = new Scanner(System.in);
		int playerCount;
		String playerName;
		this.joueurs = new LinkedList<>();

		System.out.println("Welcome to WitchHunt");
		System.out.println("************************");
		System.out.println("How many real players are there? (up to 6)");
		playerCount = askNumber(sc,0,6);
		if(playerCount>0) {
		System.out.println("Enter each player's name");
		for(int i=1;i<=playerCount;i++) {
			System.out.print("Player " + i + " : ");
			playerName = sc.next();
			joueurs.add(new Joueur(playerName));
		}
		}
		if(playerCount<MAX_PLAYER_COUNT) {
			//Modifier ajout bot : demander nombre à user
			int maxBotCount = MAX_PLAYER_COUNT - playerCount;
			int botCount;
			int minBotCount;
			if(MIN_PLAYER_COUNT-playerCount>0) {
				minBotCount = MIN_PLAYER_COUNT-playerCount;
			}
			else {
				minBotCount = 0;
			}
			System.out.println("How many bot are there? (from " + minBotCount+ " to "+maxBotCount+")");
			botCount = askNumber(sc,minBotCount,maxBotCount);
			if(botCount>0) {
				System.out.println("\n"+"Here are the names we've chosen for the bots:" + "\n");
			}
			for(int i = 0; i <botCount;i++) {
				boolean redoName = false;
				String botName;
				do {
					redoName = false;
					botName = JoueurVirtuel.getRandomName();
					Iterator<Joueur> it = joueurs.iterator();
					while(it.hasNext()) {
						if(botName==it.next().getNomJoueur()) {
							redoName = true;
						}
					}
				} while(redoName);
				joueurs.add(new JoueurVirtuel(botName));
				System.out.println(botName);
			}
		}
		
	}
	
	private int askNumber(Scanner sc, int min, int max) {
		String ErrorMessage = "Please input a number between " + min + " and " + max+".";
		System.out.println(ErrorMessage);
		while(true) {
			if(sc.hasNextInt()) {
				int nextInt = sc.nextInt();
				if(nextInt>=min && nextInt<=max) {
					return nextInt;
				}
				else {
					System.out.println(ErrorMessage);
				}
			}
			else {
				sc.next();
				System.out.println(ErrorMessage);
			}
		}
		
	}
	public void setup() {
		//distribution des cartes 
		distributionCartes();
		//affichage des cartes
		displayCards();
		//choix des rôles
		chooseIdentityCard();
	}
	private void displayCards() {
		Iterator<Joueur> it = joueurs.iterator();
		Scanner sc = new Scanner(System.in);
		while(it.hasNext()) {
			Joueur j = it.next();
			if(!j.isABot()) {
				System.out.println("\n \n"+ "Hello " + j.getNomJoueur() + ", if you're ready to look at your cards and that no one else is looking at the screen, press ENTER!");
				sc.nextLine();
				j.displayHand();
				System.out.println("\n \n"+ "When you're done, looking at them, press ENTER!");
				sc.nextLine();
				clearScreen();
			}
		}
	}
	
	private void chooseIdentityCard() {
		Iterator<Joueur> it = joueurs.iterator();
		Scanner sc = new Scanner(System.in);
		while(it.hasNext()) {
			Joueur j = it.next();
			if(!j.isABot()) {
				System.out.println("\n \n"+ "Hello " + j.getNomJoueur() + ", \n if you want to play as a Witch, type W \n if you want to play as a Villager, type V. ");
			}
		}
	}
	private void clearScreen() {
		
		//méthode doit être modifé plus tard pour être plus propre
		for (int i = 0; i < 50; ++i) System.out.println();
	}
	private void distributionCartes() {
		this.cartes = new ArrayList<>();
		Carte Broomstick = new Carte("Broomstick");
		cartes.add(Broomstick);
		cartes.add(new Carte("Angry Mob",Broomstick));
		cartes.add(new Carte ("The Inquisition"));
		cartes.add(new Carte("Pointed Hat"));
		cartes.add(new Carte("Hooked Nose"));
		Carte Wart = new Carte("Wart");
		cartes.add(Wart);
		cartes.add(new Carte("Ducking Stool",Wart));
		cartes.add(new Carte("Cauldron"));
		cartes.add(new Carte("Evil Eye"));
		cartes.add(new Carte("Toad"));
		cartes.add(new Carte("Black Cat"));
		cartes.add(new Carte("Pet Newt"));
		Collections.shuffle(cartes);
		
		float cardsPerPlayer = cartes.size() / joueurs.size();
		Iterator<Joueur> itJ = joueurs.iterator();
		Iterator<Carte> itC = cartes.iterator();
		while(itJ.hasNext()) {
			Joueur j = itJ.next();
			for(int i=0;i<cardsPerPlayer;i++) {
				if(itC.hasNext()) {
					j.ajouterCarte(itC.next());
				}
			}
		}
		while(itC.hasNext()) {
			Carte c = itC.next();
			c.setDefausse(true);
			System.out.println("The " + c.getNomCarte() + " card was discarded.");
		}
	}
	
	public static void main(String args[]) {
		Partie.getInstance().demarrerPartie();
		Partie.getInstance().setup();
	}
}
