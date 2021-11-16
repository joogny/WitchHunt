package effets;

import partie.Effet;
import partie.Joueur;

public class DiscardCardFromHand extends Effet {
	private static final String enoncé = "Discard a card from your hand.";
	public DiscardCardFromHand(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public DiscardCardFromHand(String nomEffet) {
		super(enoncé);
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
