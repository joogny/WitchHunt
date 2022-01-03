package model.effets;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;

public class TakeCardFromAccuser extends Effet {
	private static final String enoncé = "Take one card from the hand of the player who accused you.";

	public TakeCardFromAccuser() {
		super(enoncé);
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
