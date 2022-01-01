package effets;

import java.util.ArrayList;
import java.util.Iterator;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.NoPlayersToChooseFromException;
import partie.Partie;

public class RevealOrDiscard extends Effet {
	private final static String enonce = "Choose a player.They muset reveal their identity or discard a card from their hand."
			+"\nWitch: You gain 1pt. You take next turn"+
			"\nIf they discard: They take next turn";
	private final static String nomCarteBloquante = "Wart";
	public RevealOrDiscard() {
		super(enonce);
	}

	@Override
	public void activerEffet(Joueur joueurCarte, Joueur accusateur) {
		ArrayList<Joueur> joueurs = new ArrayList<>();
		joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevelées());
		joueurs.remove(joueurCarte);
		Iterator<Joueur> it = joueurs.iterator();
		joueurs.remove(joueurCarte);
		while(it.hasNext()) {
			Iterator<Carte> itC = it.next().getRevealedCards().iterator();
			while(itC.hasNext()) {
				if(itC.next().getNomCarte()==nomCarteBloquante) {
					it.remove();
				}
			}
		}
		
		Joueur j;
		try {
			j = joueurCarte.choisirJoueur(joueurs);
			if(j.estRevelee()) {
				System.out.println("We already know that " + j.toString() + " is a Villager!");
				System.out.println("They have to discard a card!");
				Carte c;
				try {
					c = j.choisirCarteAJouer();
					j.defausseCarte(c);
				} catch (NoCardsToChooseFromException e) {
					System.out.println(j.toString() + "has no cards to discard!");
				}
				System.out.println(j.toString()+ " will take next turn");
				Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
			}
			else {
				j.revealOrDiscard(joueurCarte);
			}
		} catch (NoPlayersToChooseFromException e1) {
			System.out.println("You have no one to use this effect on! The only player remaining has the " + nomCarteBloquante + " card!");
		}
		
	}

}
