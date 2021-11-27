package effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.Partie;

public class AccuserDiscards extends Effet {
	private static final String enoncé = "The player who accused you discards a random card from their hand.";

	public AccuserDiscards() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
			Carte c;
			try {
				c = accusateur.choisirCarteAJouer();
				accusateur.defausseCarte(c);
			} catch (NoCardsToChooseFromException e) {
				System.out.println(accusateur.toString() + " had no cards left to discard");
			}
			
		}
	}
	
