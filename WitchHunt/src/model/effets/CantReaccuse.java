package model.effets;

import java.util.ArrayList;

import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoPlayersToChooseFromException;
import model.partie.Partie;

public class CantReaccuse extends Effet {
	private static final String enonc� = "Choose next player.\nOn their turn they must accuse a player other than you, if possible.";

	public CantReaccuse() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(Partie.getInstance().getListeJoueurs().getJoueursNonElimin�es());
		joueurs.remove(joueurCarte);
		Joueur j;
		try {
			j = joueurCarte.choisirJoueur(joueurs);
			Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
			j.setUntargetablePlayer(joueurCarte);
		} catch (NoPlayersToChooseFromException e) {
			System.out.println("No players left!");
		}
	}


	
}
