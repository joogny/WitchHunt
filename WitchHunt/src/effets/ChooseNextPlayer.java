package effets;

import partie.Effet;
import partie.Joueur;

public class ChooseNextPlayer extends Effet {
	private static final String enonc� = "Choose next player.";
	public ChooseNextPlayer(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public ChooseNextPlayer(String nomEffet) {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
