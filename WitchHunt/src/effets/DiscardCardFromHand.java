package effets;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;

public class DiscardCardFromHand extends Effet {
	private static final String enoncé = "Discard a card from your hand.";
	public DiscardCardFromHand() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte) {
		try {
			System.out.println(joueurCarte.toString() + ": choose a card to discard!");
			Carte c = joueurCarte.choisirCarteAJouer();
			joueurCarte.defausseCarte(c);
		} catch (NoCardsToChooseFromException e) {
			System.out.println(joueurCarte.toString() + " has no cards to discard!");
		}
		
	}

}
