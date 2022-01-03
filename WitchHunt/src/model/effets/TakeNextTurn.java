package model.effets;

import model.partie.Effet;
import model.partie.Joueur;
import model.partie.Partie;

public class TakeNextTurn extends Effet {
	private static final String enoncé = "Take next turn.";

	public TakeNextTurn() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		Partie.getInstance().getListeJoueurs().movePlayerFirst(joueurCarte);
		
	}
	
}
