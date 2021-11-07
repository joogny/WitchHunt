import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Partie {
	
	private static Partie instance;
	private ArrayList<Carte> cartes;
	private LinkedList<Joueur> joueurs;
	
	private Partie() {
		
	}
	public static Partie getInstance() {
		return instance;
	}
	
	private void demarrerPartie() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to WitchHunt");
		System.out.println("************************");
		System.out.println("How many real players are there? (3 to 6)");
		int playerCount = sc.nextInt();
		
	}
	
	public static void main(String args[]) {
		System.out.println("Hello World");
		System.out.println("Hello Change number 2!");
	}
}
