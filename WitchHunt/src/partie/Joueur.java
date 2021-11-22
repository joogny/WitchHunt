package partie;
import java.util.ArrayList;
import java.util.Iterator;

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


	public void setEstSorciere(boolean role) {
		this.estSorciere=role;
	}
	public boolean isABot() {
		return false;
	}
	
	
}
