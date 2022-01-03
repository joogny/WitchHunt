package model.effets;

import model.partie.Effet;
import model.partie.Joueur;

public abstract class EffetAvantTour extends Effet{
	private Joueur joueurCarte;
	public EffetAvantTour(String nomEffet) {
		super(nomEffet);
	
	}
	public Joueur getJoueurCarte() {
		return this.joueurCarte;
	}
	public void setJoueurCarte(Joueur j ) {
		this.joueurCarte=j;
	}
	public abstract void lancerEffet(Joueur joueurVisé);
}
