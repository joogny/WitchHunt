package partie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


public class Partie {
	
	private static Partie instance;
	private ArrayList<Carte> cartes;
	private playerList listeJoueurs;
	
	private static final int MAX_PLAYER_COUNT = 6;
	private static final int MIN_PLAYER_COUNT = 3;
	private static final int MAX_SCORE = 5;
	private Scanner sc;
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
	public playerList getListeJoueurs() {
		return listeJoueurs;
	}
	public static int getMaxPlayerCount() {
		return MAX_PLAYER_COUNT;
	}
	private void askForRealPlayers() {
		System.out.println("How many real players are there? (up to 6)");
		int playerCount = askNumber(0,6);
		String playerName;
		if(playerCount>0) {
			System.out.println("Enter each player's name");
			for(int i=1;i<=playerCount;i++) {
				System.out.print("Player " + i + " : ");
				playerName = sc.next();
				listeJoueurs.addPlayer(new Joueur(playerName));
			}
		}
	}
	private void askForBots() {
		int playerCount = listeJoueurs.getPlayerCount();
		if(playerCount<MAX_PLAYER_COUNT) {
			int maxBotCount = MAX_PLAYER_COUNT - playerCount;
			int botCount;
			int minBotCount;
			if(MIN_PLAYER_COUNT-playerCount>0) {
				minBotCount = MIN_PLAYER_COUNT-playerCount;
			}
			else {
				minBotCount = 0;
			}
			System.out.println("How many bots are there? (from " + minBotCount+ " to "+maxBotCount+")");
			botCount = askNumber(minBotCount,maxBotCount);
			if(botCount>0) {
				System.out.println("\n"+"Here are the names we've chosen for the bots:" + "\n");
			}
			for(int i = 0; i <botCount;i++) {
				boolean redoName = false;
				String botName;
				do {
					redoName = false;
					botName = JoueurVirtuel.getRandomName();
					Iterator<Joueur> it = listeJoueurs.getListeJoueurs().iterator();
					while(it.hasNext()) {
						if(botName==it.next().getNomJoueur()) {
							redoName = true;
						}
					}
				} while(redoName);
				listeJoueurs.addPlayer(new JoueurVirtuel(botName));
				System.out.println(botName);
			}
		}
		System.out.println();
		
	}
	
	public void demarrerPartie() {
		boolean partieFini = false;
		while(!partieFini) {
			Joueur j = this.listeJoueurs.getFirstPlayer();
			//j.playTurn();
			System.out.println(j.toString());
			sc.nextLine();
			this.listeJoueurs.movePlayerLast(j);
		}
	}
	
	public int askNumber(int min, int max) {
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
				System.out.println(ErrorMessage);
				sc.next();
			}
		}
		
	}
	public void setup() {
		
		this.listeJoueurs = new playerList();
		System.out.println("Welcome to WitchHunt");
		System.out.println("************************");
		this.sc = new Scanner(System.in);
		this.askForRealPlayers();
		this.askForBots();
		
		
		distributionCartes();
		displayCards();
	}
	private void displayCards() {
		Iterator<Joueur> it = listeJoueurs.getListeJoueurs().iterator();
		while(it.hasNext()) {
			Joueur j = it.next();
			j.discoverHand();
			j.chooseIdentityCard();
			System.out.println();
			if(!j.isABot()) {
				System.out.println("When you're ready and want to remove your cards from the screen, press ENTER");
				sc.nextLine();
				clearScreen();
			}
		}
	}
	
	
	
	private void clearScreen() {
		
		//m�thode doit �tre modif� plus tard pour �tre plus propre
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
		
		float cardsPerPlayer = cartes.size() / listeJoueurs.getListeJoueurs().size();
		Iterator<Joueur> itJ = listeJoueurs.getListeJoueurs().iterator();
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
	
	public Scanner getScanner() {
		return sc;
	}
	
	public static void main(String args[]) {
		Partie.getInstance().setup();
		Partie.getInstance().demarrerPartie();
	}


}
