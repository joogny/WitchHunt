package effets;

import java.util.ArrayList;

import partie.Effet;
import partie.Joueur;
import partie.NoPlayersToChooseFromException;
import partie.Partie;

public class CantReaccuse extends Effet {
	private static final String enoncé = "Choose next player.\nOn their turn they must accuse a player other than you, if possible.";

	public CantReaccuse() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(Partie.getInstance().getListeJoueurs().getJoueursNonEliminées());
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
