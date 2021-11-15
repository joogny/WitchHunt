package effets;

import partie.Effet;
import partie.Joueur;

public class TakeCardFromHand extends Effet {

	public TakeCardFromHand(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(nomEffet, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public TakeCardFromHand(String nomEffet) {
		super(nomEffet);
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
