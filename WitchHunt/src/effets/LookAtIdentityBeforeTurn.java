package effets;

import partie.Effet;
import partie.Joueur;

public class LookAtIdentityBeforeTurn extends Effet {
	private static final String enonc� = "Before their turn, secretly look at their identity.";
	public LookAtIdentityBeforeTurn(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public LookAtIdentityBeforeTurn(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
