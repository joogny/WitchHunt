package effets;

import partie.Effet;
import partie.Joueur;

public class TakeCardFromAccuser extends Effet {
	private static final String enoncé = "Take one card from the hand of the player who accused you.";
	public TakeCardFromAccuser(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public TakeCardFromAccuser(String nomEffet) {
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
