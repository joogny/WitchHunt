package effets;

import partie.Effet;
import partie.Joueur;

public class TakeNextTurn extends Effet {
	private static final String enonc� = "Take next turn.";

	public TakeNextTurn() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}
	
	
	public TakeNextTurn(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean estJouable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
