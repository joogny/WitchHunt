package effets;

import partie.Effet;
import partie.Joueur;

public class CantReaccuse extends Effet {
	private static final String enonc� = "On their turn they must accuse a player other than you, if possible.";
	public CantReaccuse(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public CantReaccuse(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
