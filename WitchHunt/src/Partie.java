import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Partie {
	
	private static Partie instance;
	private ArrayList<Carte> cartes;
	private LinkedList<Joueur> joueurs;
	
	private static final int MAX_PLAYER_COUNT = 6;
	
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
		System.out.println("How many real players are there? (3 to 6)");
		while(true) {
			playerCount = sc.nextInt();
			if(playerCount<3 || playerCount>6) {
				System.out.println("Please make sure that there are 3 to 6 players");
			}
			else {
				break;
			}
		}
		
		System.out.println("Enter each player's name");
		for(int i=1;i<=playerCount;i++) {
			System.out.print("Player " + i + " : ");
			playerName = sc.next();
			joueurs.add(new Joueur(playerName));
		}
		if(playerCount<MAX_PLAYER_COUNT) {
			//Modifier ajout bot : demander nombre � user
			System.out.println("We'll add " + (MAX_PLAYER_COUNT - playerCount) + " bots to make the game more fun : ");
			for(int i = 0; i <(MAX_PLAYER_COUNT - playerCount);i++) {
				boolean redoName = false;
				String botName;
				do {
					redoName = false;
					botName = JoueurVirtuel.getRandomName();
					for(Joueur j : joueurs) {
						if(botName==j.getNomJoueur()) {
							redoName = true;
						}
					}
				} while(redoName);
				joueurs.add(new JoueurVirtuel(botName));
				System.out.println(botName);
			}
		}
		
	}
	
	public void setup() {
		//distribution des cartes 
		distributionCartes();
		//afficher carte
		//choix r�le
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
		
		Collections.shuffle(cartes);
		
		float cardsPerPlayer = cartes.size() / joueurs.size();
		
		//finir distribution
		
	}
	
	public static void main(String args[]) {
		Partie.getInstance().demarrerPartie();
		Partie.getInstance().setup();
	}
}
