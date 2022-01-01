package effets;

import java.util.ArrayList;
import java.util.LinkedList;

import partie.Effet;
import partie.Joueur;
import partie.NoPlayersToChooseFromException;
import partie.Partie;

public class ChooseNextPlayer extends Effet {
	private static final String enonc� = "Choose next player.";

	public ChooseNextPlayer() {
		super(enonc�);
	}

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		System.out.println(enonc�);
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(Partie.getInstance().getListeJoueurs().getJoueursNonElimin�es());
		joueurs.remove(joueurCarte);
		Joueur j;
		try {
			j = joueurCarte.choisirJoueur(joueurs);
			Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
			System.out.println(joueurCarte.toString() + " chose " + j.toString() + " so they will take the next turn!");
		} catch (NoPlayersToChooseFromException e) {
			System.out.println("No player left");
		}
		
	}
	
}
