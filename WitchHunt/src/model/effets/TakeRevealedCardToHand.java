package model.effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;
import model.partie.Partie;

public class TakeRevealedCardToHand extends Effet{
	private static final String enoncé = "Take one of your own revealed Rumour cards into your hand.";

	public TakeRevealedCardToHand() {
		super(enoncé);
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
