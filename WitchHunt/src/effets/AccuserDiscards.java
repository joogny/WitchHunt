package effets;

import partie.Effet;
import partie.Joueur;

public class AccuserDiscards extends Effet {
	private static final String enonc� = "The player who accused you discards a random card from their hand.";
	public AccuserDiscards(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public AccuserDiscards(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
