package effets;

import partie.Effet;
import partie.Joueur;

public class DiscardCardFromHand extends Effet {
	private static final String enonc� = "Discard a card from your hand.";
	public DiscardCardFromHand(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public DiscardCardFromHand(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}

}