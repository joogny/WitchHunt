package effets;

import partie.Effet;
import partie.Joueur;

public class RevealAnIdentity extends Effet {
	private static final String enoncé = "Choose a player. They must reveal their identity or discard a card from their hand.";
	public RevealAnIdentity(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public RevealAnIdentity(String nomEffet) {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
