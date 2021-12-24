package partie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import Vue.CartesGUI;
import Vue.NouveauJoueurGUI;
import bots.AccuserStrategy;
import bots.BotStrategy;
import bots.CartesStrategy;
import bots.JoueurVirtuel;
import bots.RandomStrategy;
import effets.DiscardCardFromHand;
import effets.RevealAnIdentity;
import effets.TakeNextTurn;


public class Partie {
	
	private static Partie instance;
	private ArrayList<Carte> cartes;
	private playerList listeJoueurs;
	private ArrayList<BotStrategy> strats;
	private static final int MAX_PLAYER_COUNT = 6;
	private static final int MIN_PLAYER_COUNT = 3;
	private Scanner sc;
	private boolean playerSetupDone;
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
	
	public String randomBotName() {
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
		return botName;
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
				System.out.println("\n"+"Here are the bots :" + "\n");
			}
			for(int i = 0; i <botCount;i++) {
				String botName = randomBotName();
				System.out.println("This bot will be named " + botName);
				System.out.println("Which strategy should " + botName + " follow?");
				for(int j =1;j<=strats.size();j++) {
					System.out.println(j + " : " + strats.get(i).toString());
				}
				int number = this.askNumber(1, strats.size());
				
				listeJoueurs.addPlayer(new JoueurVirtuel(botName,strats.get(number-1)));
				System.out.println();
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
		System.out.println("\nROUND OVER!\n");
		Joueur dernierJoueur = listeJoueurs.getJoueursNonRevelées().getFirst();
		if(dernierJoueur.estSorciere()) {
			System.out.println(dernierJoueur.toString() + " was a Witch! They gain 2 points");
			dernierJoueur.addToScore(2);
		}
		else {
			System.out.println(dernierJoueur.toString() + " was a Villager! They gain 1 point");
			dernierJoueur.addToScore(1);
		}
		// RETURN 2: La partie n'est pas terminée
		if(checkFinRound()==2) {
			this.displayEndScore();
			System.out.println("Type Y if you want to play a new Round");
			while(true) {
				String s = sc.next();
				if(s.equals("Y")) {
					Joueur j = listeJoueurs.sortedListByScore().get(0);
					this.listeJoueurs.reset();
					listeJoueurs.movePlayerFirst(j);
					distributionCartes();
					displayCards();
					demarrerPartie();
				}
			}
		}
		// RETURN 1: La partie est terminée et le Joueur 1 a gagné
		else if (checkFinRound()==1) {
			this.displayEndScore();
			System.out.println("\nGAME OVER!\n");
			Joueur j=listeJoueurs.sortedListByScore().get(0); // Joueur Gagnant
			System.out.println(j.toString()+" has "+j.getScore()+" points : He is the winner");
			System.exit(1); // La Partie est finie on sort du programme
		}
		// RETURN 0: La partie est terminée avec une égalité
		else if (checkFinRound()==0) {
			this.displayEndScore();
			System.out.println("\nGAME OVER\n");
			Joueur j1=listeJoueurs.sortedListByScore().get(0);
			Joueur j2=listeJoueurs.sortedListByScore().get(1);
			System.out.println("There is an equality between: "+j1.toString()+" and "+j2.toString());
			System.out.println("This is a Monkey Knife Fights the two players will fight to death...");
			Random rd = new Random();
			//SI TRUE J1 WIN
			if(rd.nextBoolean()) {
				System.out.println(j1.toString()+" won the fight: He is the winner");
			}
			else {
				System.out.println(j2.toString()+" won the fight: He is the winner");
			}
			System.exit(1);
		}
	}

	private void displayEndScore() {
		ArrayList<Joueur> joueurs = listeJoueurs.sortedListByScore();
		
		Iterator<Joueur> it = joueurs.iterator();
		while(it.hasNext()) {
			Joueur j = it.next();
			System.out.println(j.toString() + " : " + j.getScore() + "pts");
		}
		
		
	}
	public int checkFinRound() {
		ArrayList<Joueur> joueurs = listeJoueurs.sortedListByScore();
		
		Iterator<Joueur> it = joueurs.iterator();
		// On recupere les deux premiers joueurs de la liste triée par score
		Joueur j1=it.next();
		Joueur j2=it.next();
		// On check si la partie est finie
		if (j1.getScore()>=5) {
			if(j1.getScore()==j2.getScore()) {
				return 0; // Egalité des deux premiers joueurs RETURN 0
			}
			else {
				return 1; // Victoire de j1 RETURN 1
			}
		}
		else {
			return 2; // Personne n'a atteint 5 points, la partie n'est pas finie RETURN 2
		}
	}
	public void setup() {
		
		
		this.listeJoueurs = new playerList();
		this.strats = new ArrayList<>();
		strats.add(new RandomStrategy());
		strats.add(new AccuserStrategy());
		strats.add(new RandomStrategy());
		System.out.println("Welcome to WitchHunt");
		System.out.println("************************");
		this.sc = new Scanner(System.in);
		
		
		this.setupJoueurs();
		
		//A MODIF PLUS TARD
		//this.askForRealPlayers();
		//this.askForBots();
		
		
		distributionCartes();
		displayCards();
	}
	private void setupJoueurs() {
		NouveauJoueurGUI nouveauJoueurGUI = new NouveauJoueurGUI(listeJoueurs, strats);
		synchronized(this) {
			while(!this.playerSetupDone) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		System.out.println(listeJoueurs.getAllPlayers().toString());
	}

	private void displayCards() {
		Iterator<Joueur> it = listeJoueurs.getListeJoueurs().iterator();
		while(it.hasNext()) {
			Joueur j = it.next();
			j.discoverHand();
			//j.chooseIdentityCard(); 
			//Pas utile dans le GUI : à modif
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
		
		Carte angryMob = new Carte("Angry Mob",witchAngryMob,huntAngryMob,"./src/images/angry_mob.png.png");
		cartes.add(angryMob);//1
		
		//The Inquisition
		listeEffets witchInquisition = new listeEffets();
		witchInquisition.ajouterEffet(EffetNom.DISCARDCARDFROMHAND);
		witchInquisition.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntInquisition = new listeEffets(false,true);
		huntInquisition.ajouterEffet(EffetNom.LOOKATIDENTITYBEFORETURN);
		Carte Inquisition = new Carte("The Inquisition",witchInquisition,huntInquisition,"./src/images/the_inquisition.png");
		cartes.add(Inquisition);//2
		
		//PointedHat
		listeEffets witchPointedHat = new listeEffets(true,false);
		witchPointedHat.ajouterEffet(EffetNom.TAKEREVEALEDCARDTOHAND);
		witchPointedHat.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntPointedHat = new listeEffets(false,true);
		huntPointedHat.ajouterEffet(EffetNom.TAKEREVEALEDCARDTOHAND);
		huntPointedHat.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		Carte PointedHat = new Carte("Pointed Hat",witchPointedHat,huntPointedHat,"./src/images/pointed_hat.png");
		cartes.add(PointedHat);//3
		
		//Hooked Nose 
		listeEffets witchHookedNose = new listeEffets();
		witchHookedNose.ajouterEffet(EffetNom.TAKECARDFROMACCUSER);
		witchHookedNose.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntHookedNose = new listeEffets();
		huntHookedNose.ajouterEffet(EffetNom.TAKECARDFROMHANDBEFORETURN);
		Carte HookedNose = new Carte("Hooked Nose",witchHookedNose,huntHookedNose,"./src/images/hooked_nose.png");
		cartes.add(HookedNose);//4
		
		
		
		
		//Broomstick et Wart
		listeEffets witchBroomstickWartCauldron = new listeEffets();
		witchBroomstickWartCauldron.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntBroomstickWart = new listeEffets();
		huntBroomstickWart.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		
		Carte Broomstick = new Carte("Broomstick",witchBroomstickWartCauldron,huntBroomstickWart,"./src/images/broomstick.png");
		cartes.add(Broomstick);//5
		
		Carte Wart = new Carte("Wart",witchBroomstickWartCauldron,huntBroomstickWart,"./src/images/wart.png");
		cartes.add(Wart);//6
		
		//Ducking Stool
		listeEffets witchDuckingStool = new listeEffets();
		witchDuckingStool.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		listeEffets huntDuckingStool = new listeEffets();
		huntDuckingStool.ajouterEffet(EffetNom.REVEALORDISCARD);
		Carte DuckingStool = new Carte("Ducking Stool",witchDuckingStool,huntDuckingStool,"./src/images/ducking_stool.png");
		cartes.add(DuckingStool);//7
		
		//Cauldron
		listeEffets witchCauldron = new listeEffets();
		witchCauldron.ajouterEffet(EffetNom.ACCUSERDISCARDS);
		listeEffets huntCauldron = new listeEffets();
		huntCauldron.ajouterEffet(EffetNom.REVEALYOURIDENTITY);
		Carte Cauldron = new Carte("Cauldron",witchCauldron,huntCauldron,"./src/images/cauldron.png");
		cartes.add(Cauldron);//8

		//Evil Eye
		listeEffets witchEvilEye = new listeEffets();
		listeEffets huntEvilEye = new listeEffets();
		huntEvilEye.ajouterEffet(EffetNom.CANTREACCUSE);
		witchEvilEye.ajouterEffet(EffetNom.CANTREACCUSE);
		Carte EvilEye = new Carte("Evil Eye",witchEvilEye,huntEvilEye,"./src/images/evil_eye.png");
		cartes.add(EvilEye);//9
		
		//Toad
		listeEffets witchToad = new listeEffets();
		listeEffets huntToad = new listeEffets();
		witchToad.ajouterEffet(EffetNom.TAKENEXTTURN);
		huntToad.ajouterEffet(EffetNom.REVEALYOURIDENTITY);
		Carte Toad = new Carte("Toad",witchToad,huntToad,"./src/images/toad.png");
		cartes.add(Toad);//10
		
		//Black Cat
		listeEffets witchBlackCat = new listeEffets();
		witchBlackCat.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntBlackCat = new listeEffets();
		huntBlackCat.ajouterEffet(EffetNom.ADDDISCARDEDCARDTOHAND);
		huntBlackCat.ajouterEffet(EffetNom.TAKENEXTTURN);
		Carte BlackCat = new Carte("Black Cat",witchBlackCat,huntBlackCat,"./src/images/blackcat.png");
		cartes.add(BlackCat);//11
		
		
		//Pet Newt
		listeEffets witchPetNewt = new listeEffets();
		witchPetNewt.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntPetNewt = new listeEffets();
    	huntPetNewt.ajouterEffet(EffetNom.TAKEREVEALEDCARDFROMANYPLAYERS);
		huntPetNewt.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		Carte PetNewt = new Carte("Pet Newt",witchPetNewt,huntPetNewt,"./src/images/pet_newt.png");
		cartes.add(PetNewt);

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

		CartesGUI cartesGUI = new CartesGUI(this.cartes,this.listeJoueurs.getAllPlayers());
	
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

	public void playerSetupDone() {
		synchronized(this) {
			this.playerSetupDone=true;
			this.notifyAll();
		}

	}

	public static int getMinPlayerCount() {
		return MIN_PLAYER_COUNT;
	}
}


