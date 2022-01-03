package model.effets;

import java.util.ArrayList;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;
import model.partie.NoPlayersToChooseFromException;
import model.partie.Partie;

public class TakeCardFromHandBeforeTurn extends EffetAvantTour {
	private static final String enonc� = "Before their turn, take a random card from their hand and add it to your hand.";
	

	public TakeCardFromHandBeforeTurn() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(Partie.getInstance().getListeJoueurs().getJoueursNonElimin�es());
		joueurs.remove(joueurCarte);
		Joueur j;
		try {
			j = joueurCarte.choisirJoueur(joueurs);
			Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
			System.out.println(j.toString() + "will take next turn and will have a random card stolen!");
			super.setJoueurCarte(joueurCarte);
			j.ajouterEffetDebutTour(this);
		} catch (NoPlayersToChooseFromException e) {
			System.out.println(e);
		}
	}


	@Override
	public void lancerEffet(Joueur joueurVis�) {
		System.out.println("Because of their last card , " + super.getJoueurCarte().toString() + " will take a random card from " + joueurVis�.toString());
		ArrayList<Carte> cartes = joueurVis�.getPlayableCards();
		if(cartes.size()==0) {
			System.out.println(joueurVis�.toString() + " has no cards to take!");
		}
		else {
			int randomNumber = (int) (Math.random()*cartes.size());
			Carte c =  cartes.get(randomNumber);
			joueurVis�.removeCardFromHand(c);
			super.getJoueurCarte().addCardToHand(c);
		}
		super.setJoueurCarte(null);	
	}
	
	
}