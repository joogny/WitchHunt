package effets;

import partie.Effet;
import partie.Joueur;

public class LookAtIdentityBeforeTurn extends Effet {
	private static final String enoncé = "Before their turn, secretly look at their identity.";
	public LookAtIdentityBeforeTurn(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public LookAtIdentityBeforeTurn(String nomEffet) {
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
