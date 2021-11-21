package effets;

import partie.Effet;
import partie.Joueur;

public class AccuserDiscards extends Effet {
	private static final String enoncé = "The player who accused you discards a random card from their hand.";
	public AccuserDiscards(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public AccuserDiscards(String nomEffet) {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
