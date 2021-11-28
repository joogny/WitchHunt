package partie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import bots.JoueurVirtuel;
import bots.RandomStrategy;
import effets.DiscardCardFromHand;
import effets.RevealAnIdentity;
import effets.TakeNextTurn;


public class Partie {
	
	private static Partie instance;
	private ArrayList<Carte> cartes;
	private playerList listeJoueurs;
	
	private static final int MAX_PLAYER_COUNT = 6;
	private static final int MIN_PLAYER_COUNT = 3;
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
		int playerCount = this.askNumber(0,6);
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
			botCount = this.askNumber(minBotCount,maxBotCount);
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
				listeJoueurs.addPlayer(new JoueurVirtuel(botName,new RandomStrategy()));
				System.out.println(botName);
			}
		}
		System.out.println();
		
	}
	public void demarrerPartie() {
		boolean partieFini = false;
		while(!partieFini) {
			Joueur j = this.listeJoueurs.getFirstPlayer();
			j.playTurn();
			
			//fin de la partie
			if(listeJoueurs.getJoueursNonRevelées().size()<=1) {
				partieFini=true;
			}
			
			//cycle des joueurs
			this.listeJoueurs.movePlayerLast(j);
		}

		finPartie();			
	}
	public void finPartie() {
		System.out.println("\nGAME OVER!\n");
		Joueur dernierJoueur = listeJoueurs.getJoueursNonRevelées().getFirst();
		if(dernierJoueur.estSorciere()) {
			System.out.println(dernierJoueur.toString() + " was a Witch! They gain 2 points");
			dernierJoueur.addToScore(2);
		}
		else {
			System.out.println(dernierJoueur.toString() + " was a Villager! They gain 1 point");
			dernierJoueur.addToScore(1);
		}
		this.displayEndScore();
		//displayEndScore
	}
	private void displayEndScore() {
		ArrayList<Joueur> joueurs = listeJoueurs.sortedListByScore();
		
		Iterator<Joueur> it = joueurs.iterator();
		while(it.hasNext()) {
			Joueur j = it.next();
			System.out.println(j.toString() + " : " + j.getScore() + "pts");
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
		
		//méthode doit être modifé plus tard pour être plus propre
		for (int i = 0; i < 50; ++i) System.out.println();
	}
	private void distributionCartes() {
		
		
		
		this.cartes = new ArrayList<>();
		//AngryMob
		listeEffets witchAngryMob = new listeEffets();
		witchAngryMob.ajouterEffet(EffetNom.TAKENEXTTURN);
		
		listeEffets huntAngryMob = new listeEffets(false,true);
		huntAngryMob.ajouterEffet(EffetNom.REVEALANIDENTITY);
		
		Carte angryMob = new Carte("Angry Mob",witchAngryMob,huntAngryMob);
		cartes.add(angryMob);
		
		//The Inquisition
		listeEffets witchInquisition = new listeEffets();
		witchInquisition.ajouterEffet(EffetNom.DISCARDCARDFROMHAND);
		witchInquisition.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntInquisition = new listeEffets(false,true);
		huntInquisition.ajouterEffet(EffetNom.LOOKATIDENTITYBEFORETURN);
		Carte Inquisition = new Carte("The Inquisition",witchInquisition,huntInquisition);
		cartes.add(Inquisition);
		
		//PointedHat
		listeEffets witchPointedHat = new listeEffets(true,false);
		witchPointedHat.ajouterEffet(EffetNom.TAKEREVEALEDCARDTOHAND);
		witchPointedHat.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntPointedHat = new listeEffets(false,true);
		huntPointedHat.ajouterEffet(EffetNom.TAKEREVEALEDCARDTOHAND);
		huntPointedHat.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		Carte PointedHat = new Carte("Pointed Hat",witchPointedHat,huntPointedHat);
		cartes.add(PointedHat);
		
		//Hooked Nose 
		listeEffets witchHookedNose = new listeEffets();
		witchHookedNose.ajouterEffet(EffetNom.TAKECARDFROMACCUSER);
		witchHookedNose.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntHookedNose = new listeEffets();
		huntHookedNose.ajouterEffet(EffetNom.TAKECARDFROMHANDBEFORETURN);
		Carte HookedNose = new Carte("Hooked Nose",witchHookedNose,huntHookedNose);
		cartes.add(HookedNose);
		
		
		
		
		//Broomstick et Wart
		listeEffets witchBroomstickWartCauldron = new listeEffets();
		witchBroomstickWartCauldron.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntBroomstickWart = new listeEffets();
		huntBroomstickWart.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		
		Carte Broomstick = new Carte("Broomstick",witchBroomstickWartCauldron,huntBroomstickWart);
		cartes.add(Broomstick);
		
		Carte Wart = new Carte("Wart",witchBroomstickWartCauldron,huntBroomstickWart);
		cartes.add(Wart);
		
		//Ducking Stool
		listeEffets witchDuckingStool = new listeEffets();
		witchDuckingStool.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		listeEffets huntDuckingStool = new listeEffets();
		huntDuckingStool.ajouterEffet(EffetNom.REVEALORDISCARD);
		Carte DuckingStool = new Carte("Ducking Stool",witchDuckingStool,huntDuckingStool);
		cartes.add(DuckingStool);
		
		//Cauldron
		listeEffets witchCauldron = new listeEffets();
		witchCauldron.ajouterEffet(EffetNom.ACCUSERDISCARDS);
		listeEffets huntCauldron = new listeEffets();
		huntCauldron.ajouterEffet(EffetNom.REVEALYOURIDENTITY);
		Carte Cauldron = new Carte("Cauldron",witchCauldron,huntCauldron);
		cartes.add(Cauldron);

		//Evil Eye
		listeEffets witchEvilEye = new listeEffets();
		listeEffets huntEvilEye = new listeEffets();
		huntEvilEye.ajouterEffet(EffetNom.CANTREACCUSE);
		witchEvilEye.ajouterEffet(EffetNom.CANTREACCUSE);
		Carte EvilEye = new Carte("Evil Eye",witchEvilEye,huntEvilEye);
		cartes.add(EvilEye);
		
		//Toad
		listeEffets witchToad = new listeEffets();
		listeEffets huntToad = new listeEffets();
		witchToad.ajouterEffet(EffetNom.TAKENEXTTURN);
		huntToad.ajouterEffet(EffetNom.REVEALYOURIDENTITY);
		Carte Toad = new Carte("Toad",witchToad,huntToad);
		cartes.add(Toad);
		
		//Black Cat
		listeEffets witchBlackCat = new listeEffets();
		witchBlackCat.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntBlackCat = new listeEffets();
		huntBlackCat.ajouterEffet(EffetNom.ADDDISCARDEDCARDTOHAND);
		huntBlackCat.ajouterEffet(EffetNom.TAKENEXTTURN);
		Carte BlackCat = new Carte("Black Cat",witchBlackCat,huntBlackCat);
		cartes.add(BlackCat);
		
		
		//Pet Newt
		listeEffets witchPetNewt = new listeEffets();
		witchPetNewt.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntPetNewt = new listeEffets();
		huntPetNewt.ajouterEffet(EffetNom.TAKEREVEALEDCARDFROMANYPLAYERS);
		huntPetNewt.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
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

	public void eliminerJoueur(Joueur joueur) {
		System.out.println(joueur.toString() + " is out of the game! \n");
		listeJoueurs.eliminerJoueur(joueur);
	}

	public ArrayList<Carte> getDiscardedCards() {
		ArrayList<Carte> listeCartes = new ArrayList<>();
		Iterator<Carte> it = cartes.iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.isDefausse()) {
				listeCartes.add(c);
			}
		}
		return listeCartes;
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
}


