package effets;

import partie.Effet;
import partie.Joueur;

public class TakeCardFromHandBeforeTurn extends Effet {
	private static final String enonc� = "Before their turn, take a random card from their hand and add it to your hand.";
	public TakeCardFromHandBeforeTurn(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public TakeCardFromHandBeforeTurn(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
