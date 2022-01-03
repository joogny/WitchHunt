package view;

import java.util.ArrayList;
import java.util.Iterator;

import model.partie.Carte;
import model.partie.Joueur;

public class ChoixJoueurCLI extends NumberBasedCLI{
	private ArrayList<Joueur> listeJoueurs;
	private Joueur j;
	public ChoixJoueurCLI(Joueur j, ArrayList<Joueur> listeJoueurs) {
		this.j=j;
		this.listeJoueurs=listeJoueurs;
		this.min=1;
		this.max=listeJoueurs.size();
	}
	@Override
	public void setup() {
		System.out.println("Choose a player");
		this.ErrorMessage();
		Iterator<Joueur> it = listeJoueurs.iterator();
		int index=1;
		while(it.hasNext()) {
			System.out.println(index + " : "  +it.next().toString());
			index++;
		}
		
	}

	@Override
	protected void passValue(int nb) {
		j.setChosenPlayer(listeJoueurs.get(nb));
	}

}
