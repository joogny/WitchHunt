package effets;

import partie.Effet;
import partie.Joueur;

public class TakeCardFromHandBeforeTurn extends Effet {
	private static final String enoncé = "Before their turn, take a random card from their hand and add it to your hand.";
	

	public TakeCardFromHandBeforeTurn(String nomEffet) {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// TODO Auto-generated method stub
		
	}
	
}
