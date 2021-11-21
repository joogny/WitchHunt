package effets;

import partie.Effet;
import partie.Joueur;

public class ChooseNextPlayer extends Effet {
	private static final String enoncé = "Choose next player.";
	public ChooseNextPlayer(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public ChooseNextPlayer(String nomEffet) {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
