package model.effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;
import model.partie.Partie;

public class AccuserDiscards extends Effet {
	private static final String enoncé = "The player who accused you discards a random card from their hand.";

	public AccuserDiscards() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte, Joueur accusateur) {
		Carte c;
		ArrayList<Carte> cartes;
		System.out.println(enoncé);
		cartes = accusateur.getMain();
		if (cartes.size() == 0) {
			System.out.println(accusateur.toString() + " had no cards left to discard");
		} else {
		    Random rand = new Random();
		    c = cartes.get(rand.nextInt(cartes.size()));
		    joueurCarte.defausseCarte(c);
		    
		}
	}
}
