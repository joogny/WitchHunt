package effets;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class ChooseNextPlayer extends Effet {
	private static final String enonc� = "Choose next player.";

	public ChooseNextPlayer() {
		super(enonc�);
	}

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		System.out.println(enonc�);
		Joueur j = joueurCarte.choisirJoueur();
		Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
		System.out.println(joueurCarte.toString() + " chose " + j.toString() + " so they will take the next turn!");
	}
	
}
