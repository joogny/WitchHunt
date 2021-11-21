package effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class TakeRevealedCardToHand extends Effet{
	private static final String enoncé = "Take one of your own revealed Rumour cards into your hand.";
	public TakeRevealedCardToHand(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		super(enoncé, needCarteRevelee, needVillageoisRevelee);
		// TODO Auto-generated constructor stub
	}

	public TakeRevealedCardToHand() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		ArrayList<Carte> revealedCards = new ArrayList<>();
		
		Iterator<Carte> it = joueurCarte.getMain().iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.isRevelee()) {
				revealedCards.add(c);
			}
		}
		
		if(revealedCards.size()==0) {
			System.out.println("You have no revealed cards");
		}
		else {
			int index=0;
			it = revealedCards.iterator();
			while(it.hasNext()) {
				index++;
				System.out.println(index+ " : " +it.next().getNomCarte());
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
