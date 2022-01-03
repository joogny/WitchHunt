package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import model.partie.Carte;
import model.partie.Joueur;

@SuppressWarnings("deprecation")
public class ChoixRoleCLI implements CLI, Observer {


	private Joueur joueur;
	public ChoixRoleCLI(ArrayList<Joueur> j) {
		Iterator<Joueur> it = j.iterator();
		while(it.hasNext()) {
			it.next().addObserver(this);
		}
	}
	@Override
	public void action(String saisie) {
		if(saisie.equals("W")) {
			joueur.setEstSorciere(true);
		}
		else if(saisie.equals("V")) {
			joueur.setEstSorciere(false);
		}
		else {
			System.out.println("Please type either W or V");
		}
	}

	@Override
	public void setup() {
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Joueur) {
			this.joueur=(Joueur) o;
			System.out.println(this.joueur.getNomJoueur()+", please choose a role!");
			System.out.println("Here are you cards: ");
			Iterator<Carte> it = joueur.getCards().iterator();
			while(it.hasNext()) {
				System.out.println(it.next().affichageCarte()+"\n");
			}
			System.out.println("Type W to play as a Witch or V to play as a Villager");
		}
	}
	
}
