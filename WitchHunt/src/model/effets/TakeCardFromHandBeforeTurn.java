package model.effets;

import java.util.ArrayList;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;
import model.partie.NoPlayersToChooseFromException;
import model.partie.Partie;

public class TakeCardFromHandBeforeTurn extends EffetAvantTour {
	private static final String enoncé = "Before their turn, take a random card from their hand and add it to your hand.";
	

	public TakeCardFromHandBeforeTurn() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(Partie.getInstance().getListeJoueurs().getJoueursNonEliminées());
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
	public void lancerEffet(Joueur joueurVisé) {
		System.out.println("Because of their last card , " + super.getJoueurCarte().toString() + " will take a random card from " + joueurVisé.toString());
		ArrayList<Carte> cartes = joueurVisé.getPlayableCards();
		if(cartes.size()==0) {
			System.out.println(joueurVisé.toString() + " has no cards to take!");
		}
		else {
			int randomNumber = (int) (Math.random()*cartes.size());
			Carte c =  cartes.get(randomNumber);
			joueurVisé.removeCardFromHand(c);
			super.getJoueurCarte().addCardToHand(c);
		}
		super.setJoueurCarte(null);	
	}
	
	
}
