package effets;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class CantReaccuse extends Effet {
	private static final String enoncé = "Choose next player.\nOn their turn they must accuse a player other than you, if possible.";

	public CantReaccuse() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		Joueur j = joueurCarte.choisirJoueur();
		System.out.println(j.toString() + " will take next turn!");
		Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
		j.setUntargetablePlayer(joueurCarte);
	}


	
}
