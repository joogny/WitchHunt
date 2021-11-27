package effets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class AccuserDiscards extends Effet {
	private static final String enoncé = "The player who accused you discards a random card from their hand.";

	public AccuserDiscards(String nomEffet) {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
			Joueur accuser = Partie.getInstance().getListeJoueurs().getAccuser(); //A MODIF
			if(accuser!=null) {
			ArrayList<Carte> cartes = accuser.getPlayableCards();
			if(cartes.size()!=0) {
				int randomNum = (int)(Math.random() * (cartes.size() + 1));
				Carte c = cartes.get(randomNum);
				c.setDefausse(true);
				System.out.println(c.toString() +"\n  was discarded from " + accuser.toString()+"'s hand");
			}
			else {
				System.out.println(accuser.toString() + "didn't have any cards to discard");
			}
		}
	}
	
}
