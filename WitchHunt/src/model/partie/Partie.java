package model.partie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import model.bots.AccuserStrategy;
import model.bots.BotStrategy;
import model.bots.CartesStrategy;
import model.bots.JoueurVirtuel;
import model.bots.RandomStrategy;
import model.effets.DiscardCardFromHand;
import model.effets.RevealAnIdentity;
import model.effets.TakeNextTurn;
import view.ChoixRoleCLI;
import view.ChoixRoleGUI;
import view.NouveauJoueurCLI;
import view.NouveauJoueurGUI;
import view.TextInterface;

@SuppressWarnings("deprecation")
public class Partie extends Observable {

	private static Partie instance;
	private ArrayList<Carte> cartes;
	private playerList listeJoueurs;
	private ArrayList<BotStrategy> strats;
	private static final int MAX_PLAYER_COUNT = 6;
	private static final int MIN_PLAYER_COUNT = 3;
	private boolean playerSetupDone;
	private TextInterface textInterface;

	private Partie() {
	}

	public static Partie getInstance() {
		if (instance == null) {
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

	public String randomBotName() {
		boolean redoName = false;
		String botName;
		do {
			redoName = false;
			botName = JoueurVirtuel.getRandomName();
			Iterator<Joueur> it = listeJoueurs.getListeJoueurs().iterator();
			while (it.hasNext()) {
				if (botName == it.next().getNomJoueur()) {
					redoName = true;
				}
			}
		} while (redoName);
		return botName;
	}

	public void demarrerPartie() {
		boolean partieFini = false;
		while (!partieFini) {
			Joueur j = this.listeJoueurs.getFirstPlayer();
			j.playTurn();
			// fin de la partie
			
			if (listeJoueurs.getJoueursNonRevelées().size() <= 1) {
				partieFini = true;
			}

			// cycle des joueurs
			this.listeJoueurs.movePlayerLast(j);
		}

		finPartie();
	}

	public void finPartie() {
		System.out.println("\nROUND OVER!\n");
		Joueur dernierJoueur = listeJoueurs.getJoueursNonRevelées().getFirst();
		if (dernierJoueur.estSorciere()) {
			System.out.println(dernierJoueur.toString() + " was a Witch! They gain 2 points");
			dernierJoueur.addToScore(2);
		} else {
			System.out.println(dernierJoueur.toString() + " was a Villager! They gain 1 point");
			dernierJoueur.addToScore(1);
		}
		// RETURN 2: La partie n'est pas terminée
		if (checkFinRound() == 2) {
			this.displayEndScore();
			System.out.println("New round!");

			Joueur j = listeJoueurs.sortedListByScore().get(0);
			this.listeJoueurs.reset();
			listeJoueurs.movePlayerFirst(j);
			distributionCartes();
			chooseRoles();
			demarrerPartie();

		}
		// RETURN 1: La partie est terminée et le Joueur 1 a gagné
		else if (checkFinRound() == 1) {
			this.displayEndScore();
			System.out.println("\nGAME OVER!\n");
			Joueur j = listeJoueurs.sortedListByScore().get(0); // Joueur Gagnant
			System.out.println(j.toString() + " has " + j.getScore() + " points : He is the winner");
			System.exit(1); // La Partie est finie on sort du programme
		}
		// RETURN 0: La partie est terminée avec une égalité
		else if (checkFinRound() == 0) {
			this.displayEndScore();
			System.out.println("\nGAME OVER\n");
			Joueur j1 = listeJoueurs.sortedListByScore().get(0);
			Joueur j2 = listeJoueurs.sortedListByScore().get(1);
			System.out.println("There is an equality between: " + j1.toString() + " and " + j2.toString());
			System.out.println("This is a Monkey Knife Fights the two players will fight to death...");
			Random rd = new Random();
			// SI TRUE J1 WIN
			if (rd.nextBoolean()) {
				System.out.println(j1.toString() + " won the fight: He is the winner");
			} else {
				System.out.println(j2.toString() + " won the fight: He is the winner");
			}
			System.exit(1);
		}
	}

	private void displayEndScore() {
		ArrayList<Joueur> joueurs = listeJoueurs.sortedListByScore();

		Iterator<Joueur> it = joueurs.iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			System.out.println(j.toString() + " : " + j.getScore() + "pts");
		}

	}

	public int checkFinRound() {
		ArrayList<Joueur> joueurs = listeJoueurs.sortedListByScore();

		Iterator<Joueur> it = joueurs.iterator();
		// On recupere les deux premiers joueurs de la liste triée par score
		Joueur j1 = it.next();
		Joueur j2 = it.next();
		// On check si la partie est finie
		if (j1.getScore() >= 5) {
			if (j1.getScore() == j2.getScore()) {
				return 0; // Egalité des deux premiers joueurs RETURN 0
			} else {
				return 1; // Victoire de j1 RETURN 1
			}
		} else {
			return 2; // Personne n'a atteint 5 points, la partie n'est pas finie RETURN 2
		}
	}

	public void setup() {

		this.listeJoueurs = new playerList();
		this.strats = new ArrayList<>();
		strats.add(new RandomStrategy());
		strats.add(new AccuserStrategy());
		strats.add(new CartesStrategy());
		System.out.println("Welcome to WitchHunt");
		System.out.println("************************");

		this.textInterface = new TextInterface();
		this.setupJoueurs();

		distributionCartes();
		chooseRoles();
	}

	private void setupJoueurs() {
		NouveauJoueurGUI nouveauJoueurGUI = new NouveauJoueurGUI(listeJoueurs, strats, this);
		NouveauJoueurCLI nouveauJoueurCLI = new NouveauJoueurCLI(listeJoueurs, strats, this);
		this.textInterface.setCLI(nouveauJoueurCLI);
		synchronized (this) {
			while (!this.playerSetupDone) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		this.textInterface.setCLI(null);
	}

	public TextInterface getTextInterface() {
		return this.textInterface;
	}

	private void chooseRoles() {
		ChoixRoleGUI GUI = new ChoixRoleGUI(listeJoueurs.getAllPlayers());
		ChoixRoleCLI CLI = new ChoixRoleCLI(listeJoueurs.getAllPlayers());
		this.textInterface.setCLI(CLI);
		Iterator<Joueur> it = listeJoueurs.getListeJoueurs().iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			if (!j.isABot()) {
				j.updatePlayer();
				synchronized (j) {
					while (j.estSorciere() == null) {
						try {
							j.wait();
						} catch (InterruptedException e) {
						}
					}
				}
			} else {
				Random rd = new Random();
				j.setEstSorciere(rd.nextBoolean());
			}
		}
		GUI.hide();
		this.textInterface.setCLI(null);
	}


	private void distributionCartes() {

		this.cartes = new ArrayList<>();
		// AngryMob
		listeEffets witchAngryMob = new listeEffets();
		witchAngryMob.ajouterEffet(EffetNom.TAKENEXTTURN);

		listeEffets huntAngryMob = new listeEffets(false, true);
		huntAngryMob.ajouterEffet(EffetNom.REVEALANIDENTITY);

		Carte angryMob = new Carte("Angry Mob", witchAngryMob, huntAngryMob, "./src/view/images/angry_mob.png");
		cartes.add(angryMob);// 1

		// The Inquisition
		listeEffets witchInquisition = new listeEffets();
		witchInquisition.ajouterEffet(EffetNom.DISCARDCARDFROMHAND);
		witchInquisition.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntInquisition = new listeEffets(false, true);
		huntInquisition.ajouterEffet(EffetNom.LOOKATIDENTITYBEFORETURN);
		Carte Inquisition = new Carte("The Inquisition", witchInquisition, huntInquisition,
				"./src/view/images/the_inquisition.png");
		cartes.add(Inquisition);// 2

		// PointedHat
		listeEffets witchPointedHat = new listeEffets(true, false);
		witchPointedHat.ajouterEffet(EffetNom.TAKEREVEALEDCARDTOHAND);
		witchPointedHat.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntPointedHat = new listeEffets(false, true);
		huntPointedHat.ajouterEffet(EffetNom.TAKEREVEALEDCARDTOHAND);
		huntPointedHat.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		Carte PointedHat = new Carte("Pointed Hat", witchPointedHat, huntPointedHat, "./src/view/images/pointed_hat.png");
		cartes.add(PointedHat);// 3

		// Hooked Nose
		listeEffets witchHookedNose = new listeEffets();
		witchHookedNose.ajouterEffet(EffetNom.TAKECARDFROMACCUSER);
		witchHookedNose.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntHookedNose = new listeEffets();
		huntHookedNose.ajouterEffet(EffetNom.TAKECARDFROMHANDBEFORETURN);
		Carte HookedNose = new Carte("Hooked Nose", witchHookedNose, huntHookedNose, "./src/view/images/hooked_nose.png");
		cartes.add(HookedNose);// 4

		// Broomstick et Wart
		listeEffets witchBroomstickWart = new listeEffets();
		witchBroomstickWart.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntBroomstickWart = new listeEffets();
		huntBroomstickWart.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);

		Carte Broomstick = new Carte("Broomstick", witchBroomstickWart, huntBroomstickWart,
				"./src/view/images/broomstick.png");
		cartes.add(Broomstick);// 5

		Carte Wart = new Carte("Wart", witchBroomstickWart, huntBroomstickWart, "./src/view/images/wart.png");
		cartes.add(Wart);// 6

		// Ducking Stool
		listeEffets witchDuckingStool = new listeEffets();
		witchDuckingStool.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		listeEffets huntDuckingStool = new listeEffets();
		huntDuckingStool.ajouterEffet(EffetNom.REVEALORDISCARD);
		Carte DuckingStool = new Carte("Ducking Stool", witchDuckingStool, huntDuckingStool,
				"./src/view/images/ducking_stool.png");
		cartes.add(DuckingStool);// 7

		// Cauldron
		listeEffets witchCauldron = new listeEffets();
		witchCauldron.ajouterEffet(EffetNom.ACCUSERDISCARDS);
		witchCauldron.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntCauldron = new listeEffets();
		huntCauldron.ajouterEffet(EffetNom.REVEALYOURIDENTITY);
		Carte Cauldron = new Carte("Cauldron", witchCauldron, huntCauldron, "./src/view/images/cauldron.png");
		cartes.add(Cauldron);// 8

		// Evil Eye
		listeEffets witchEvilEye = new listeEffets();
		listeEffets huntEvilEye = new listeEffets();
		huntEvilEye.ajouterEffet(EffetNom.CANTREACCUSE);
		witchEvilEye.ajouterEffet(EffetNom.CANTREACCUSE);
		Carte EvilEye = new Carte("Evil Eye", witchEvilEye, huntEvilEye, "./src/view/images/evil_eye.png");
		cartes.add(EvilEye);// 9

		// Toad
		listeEffets witchToad = new listeEffets();
		listeEffets huntToad = new listeEffets();
		witchToad.ajouterEffet(EffetNom.TAKENEXTTURN);
		huntToad.ajouterEffet(EffetNom.REVEALYOURIDENTITY);
		Carte Toad = new Carte("Toad", witchToad, huntToad, "./src/view/images/toad.png");
		cartes.add(Toad);// 10

		// Black Cat
		listeEffets witchBlackCat = new listeEffets();
		witchBlackCat.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntBlackCat = new listeEffets();
		huntBlackCat.ajouterEffet(EffetNom.ADDDISCARDEDCARDTOHAND);
		huntBlackCat.ajouterEffet(EffetNom.TAKENEXTTURN);
		Carte BlackCat = new Carte("Black Cat", witchBlackCat, huntBlackCat, "./src/view/images/blackcat.png");
		cartes.add(BlackCat);// 11

		// Pet Newt
		listeEffets witchPetNewt = new listeEffets();
		witchPetNewt.ajouterEffet(EffetNom.TAKENEXTTURN);
		listeEffets huntPetNewt = new listeEffets();
		huntPetNewt.ajouterEffet(EffetNom.TAKEREVEALEDCARDFROMANYPLAYERS);
		huntPetNewt.ajouterEffet(EffetNom.CHOOSENEXTPLAYER);
		Carte PetNewt = new Carte("Pet Newt", witchPetNewt, huntPetNewt, "./src/view/images/pet_newt.png");
		cartes.add(PetNewt);

		Collections.shuffle(cartes);

		float cardsPerPlayer = cartes.size() / listeJoueurs.getListeJoueurs().size();

		Iterator<Joueur> itJ = listeJoueurs.getListeJoueurs().iterator();
		Iterator<Carte> itC = cartes.iterator();
		while (itJ.hasNext()) {
			Joueur j = itJ.next();
			for (int i = 0; i < cardsPerPlayer; i++) {
				if (itC.hasNext()) {
					j.ajouterCarte(itC.next());
				}
			}
		}
		while (itC.hasNext()) {
			Carte c = itC.next();
			c.setDefausse(true);
			System.out.println("The " + c.getNomCarte() + " card was discarded.");
		}

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
		while (it.hasNext()) {
			Carte c = it.next();
			if (c.isDefausse()) {
				listeCartes.add(c);
			}
		}
		return listeCartes;
	}


	public void playerSetupDone() {
		synchronized (this) {
			this.playerSetupDone = true;
			this.notifyAll();
			this.setChanged();
			this.notifyObservers();
		}

	}

	public static int getMinPlayerCount() {
		return MIN_PLAYER_COUNT;
	}

	public boolean getPlayerSetupDone() {
		return this.playerSetupDone;
	}
}
