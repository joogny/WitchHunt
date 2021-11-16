package effets;

import partie.Effet;
import partie.Joueur;

public class TakeRevealedCardToHand extends Effet{
	private static final String enoncé = "Take one of your own revealed Rumour cards into your hand.";
	public TakeRevealedCardToHand(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public TakeRevealedCardToHand() {
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
