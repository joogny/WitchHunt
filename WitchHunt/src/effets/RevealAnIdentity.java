package effets;

import partie.Effet;
import partie.Joueur;

public class RevealAnIdentity extends Effet {
	private static final String enonc� = "Choose a player. They must reveal their identity or discard a card from their hand.";
	public RevealAnIdentity(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public RevealAnIdentity(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
