package effets;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class ChooseNextPlayer extends Effet {
	private static final String enoncé = "Choose next player.";

	public ChooseNextPlayer() {
		super(enoncé);
	}

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		System.out.println(enoncé);
		Joueur j = joueurCarte.choisirJoueur();
		Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
		System.out.println(joueurCarte.toString() + " chose " + j.toString() + " so they will take the next turn!");
	}
	
}
