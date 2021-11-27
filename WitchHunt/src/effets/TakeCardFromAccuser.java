package effets;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;

public class TakeCardFromAccuser extends Effet {
	private static final String enonc� = "Take one card from the hand of the player who accused you.";

	public TakeCardFromAccuser() {
		super(enonc�);
	}

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		try {
			Carte c = joueurCarte.choisirCarte(accusateur.getPlayableCards());
			accusateur.removeCardFromHand(c);
			joueurCarte.addCardToHand(c);
		} catch (NoCardsToChooseFromException e) {
			System.out.println(accusateur.toString() + "doesn't have any cards to choose from!");
		}
	}
	
}
