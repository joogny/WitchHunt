package model.effets;

import java.util.ArrayList;
import java.util.Iterator;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;
import model.partie.Partie;

public class AddDiscardedCardToHand extends Effet {

	private static final String énoncé = "Add one discarded card to your hand, and then discard this card.";
	
	public AddDiscardedCardToHand() {
		super(énoncé);
	}
	
	
	@Override
	public void activerEffet(Joueur joueurCarte, Joueur accusateur) {
		ArrayList<Carte> cartes = Partie.getInstance().getDiscardedCards();
		if(cartes.size()==0) {
			System.out.println("There are no discarded cards!");
		}
		else {
			try {
				Carte c = joueurCarte.choisirCarte(cartes);
				Iterator<Joueur> it = Partie.getInstance().getListeJoueurs().getListeJoueurs().iterator();
				while(it.hasNext()) {
					Joueur j  = it.next();
					if(j.getMain().contains(c)) {
						j.getMain().remove(c);
					}
				}
				c.setDefausse(false);
				c.setRevelee(false);
				joueurCarte.addCardToHand(c);
			} catch (NoCardsToChooseFromException e) {
				System.out.println("There are no discarded cards!");
			}			
		}

	}

}
