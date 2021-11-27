package effets;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class TakeNextTurn extends Effet {
	private static final String enoncé = "Take next turn.";

	public TakeNextTurn() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		Partie.getInstance().getListeJoueurs().movePlayerFirst(joueurCarte);
		System.out.println(joueurCarte.getNomJoueur()+" will take the next turn");
		
	}
	
}
