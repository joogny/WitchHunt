package model.effets;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;

public class DiscardCardFromHand extends Effet {
	private static final String enoncé = "Discard a card from your hand.";
	public DiscardCardFromHand() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		try {
			System.out.println(joueurCarte.toString() + ": choose a card to discard!");
			Carte c = joueurCarte.choisirCarteAJouer();
			joueurCarte.defausseCarte(c);
		} catch (NoCardsToChooseFromException e) {
			System.out.println(joueurCarte.toString() + " has no cards to discard!");
		}
		
	}

}
