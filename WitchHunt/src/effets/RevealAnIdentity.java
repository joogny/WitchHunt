package effets;

import java.util.ArrayList;
import java.util.Iterator;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class RevealAnIdentity extends Effet {
	private static final String enonc� = "Reveal another player's identity. \nWitch: You gain 2pts. You take next turn. \nVillager:You lose 2pts. They take next turn.";

	public RevealAnIdentity() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		Joueur j = joueurCarte.choisirJoueurNonRevelee();
		j.setEstRevele();
		if(j.estSorciere()) {
			joueurCarte.addToScore(2);
		}
		else {
			if(joueurCarte.getScore()<=2) {
				joueurCarte.setScore(0);
			}
			else {
				joueurCarte.setScore(joueurCarte.getScore()-2);
			}
		}
	}
	
	
}
