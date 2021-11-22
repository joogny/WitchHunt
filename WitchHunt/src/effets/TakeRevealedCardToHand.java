package effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class TakeRevealedCardToHand extends Effet{
	private static final String enonc� = "Take one of your own revealed Rumour cards into your hand.";
	public TakeRevealedCardToHand(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enonc�, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public TakeRevealedCardToHand() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		ArrayList<Carte> revealedCards = joueurCarte.getRevealedCards();
		if(revealedCards.size()==0) {
			System.out.println("You have no revealed cards");
		}
		else {
			int index=0;
			Iterator<Carte> it = revealedCards.iterator();
			while(it.hasNext()) {
				index++;
				System.out.println(index+ " : \n" +it.next().toString());
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("Please enter the number of the card you want");
			int nbCarte = Partie.getInstance().askNumber(sc, 1, index);
			
			Carte carteChoisie = revealedCards.get(nbCarte-1);
			
			System.out.println("You chose to take "+ carteChoisie.getNomCarte() +" back to your hand");
			carteChoisie.setRevelee(false);
			
		}
	}

}
