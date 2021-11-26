package effets;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class TakeNextTurn extends Effet {
	private static final String enonc� = "Take next turn.";

	public TakeNextTurn() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void activerEffet(Joueur joueurCarte) {
		Partie.getInstance().getListeJoueurs().movePlayerFirst(joueurCarte);
		System.out.println(joueurCarte.getNomJoueur()+" will take the next turn");
		
	}
	
}
