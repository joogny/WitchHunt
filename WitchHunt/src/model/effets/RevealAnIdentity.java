package model.effets;

import java.util.ArrayList;
import java.util.Iterator;

import model.partie.Carte;
import model.partie.Effet;
import model.partie.Joueur;
import model.partie.NoPlayersToChooseFromException;
import model.partie.Partie;

public class RevealAnIdentity extends Effet {
	private static final String enonc� = "Reveal another player's identity. \nWitch: You gain 2pts. You take next turn. \nVillager:You lose 2pts. They take next turn.";
	private static final String nomCarteBloquante = "Broomstick";

	public RevealAnIdentity() {
		super(enonc�);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		Joueur j;
		try {
			ArrayList<Joueur> joueurs = new ArrayList<>();
			joueurs.addAll(Partie.getInstance().getListeJoueurs().getJoueursNonRevel�es());
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
			
			j = joueurCarte.choisirJoueur(joueurs);
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
		} catch (NoPlayersToChooseFromException e) {
			System.out.println("You have no one to use this effect on! The only player remaining has the " + nomCarteBloquante + " card!");
		}
	}
	
	
}
