package effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.Partie;

public class TakeRevealedCardToHand extends Effet{
	private static final String enonc� = "Take one of your own revealed Rumour cards into your hand.";

	public TakeRevealedCardToHand() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		ArrayList<Carte> revealedCards = joueurCarte.getRevealedCards();
 		if(revealedCards.size()==0) {
			System.out.println(joueurCarte.toString()  +" has no revealed cards");
		}
		else {
			Carte carteChoisie;
			try {
				carteChoisie = joueurCarte.choisirCarte(revealedCards);
				System.out.println("You chose to take "+ carteChoisie.getNomCarte() +" back to your hand");
				carteChoisie.setRevelee(false);
			} catch (NoCardsToChooseFromException e) {
				System.out.println(joueurCarte.toString()  +" has no revealed cards");
			}
			
		}
	}

}
