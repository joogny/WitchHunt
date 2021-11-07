import java.util.ArrayList;

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
	
	
	
	public void setMain(ArrayList<Carte> main) {
		this.main=main;
	}
}
