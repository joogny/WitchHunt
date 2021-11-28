package effets;

import java.util.ArrayList;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.Partie;

public class TakeCardFromHandBeforeTurn extends EffetAvantTour {
	private static final String enoncé = "Before their turn, take a random card from their hand and add it to your hand.";
	

	public TakeCardFromHandBeforeTurn() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		Joueur j = joueurCarte.choisirJoueur();
		Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
		System.out.println(j.toString() + "will take next turn and will have a random card stolen!");
		super.setJoueurCarte(joueurCarte);
		j.ajouterEffetDebutTour(this);
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
