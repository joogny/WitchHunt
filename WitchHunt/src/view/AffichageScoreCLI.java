package view;

import java.util.ArrayList;
import java.util.Iterator;

import model.partie.Joueur;
import model.partie.Partie;

public class AffichageScoreCLI implements CLI{
	private ArrayList<Joueur> listeJoueurs;
	
	public AffichageScoreCLI(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs=listeJoueurs;
	}
	
	public void setup() {
		Iterator<Joueur> it = this.listeJoueurs.iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			System.out.println(j.toString() + " : " + j.getScore() + "pts");
		}
		System.out.println("Would you like to pass to the next step? Press Y if you want: ");
	}
	
	public void ErrorMessage() {
		System.out.println("Would you like to pass to the next step? Press Y if you want: ");
	}
	
	public void passValue(String newRound) {
		Partie.getInstance().setNewRound(newRound);
	}
	
	@Override
	public void action(String saisie) {
		if(saisie==null) {
			System.out.println("La saisie");
			ErrorMessage();
		}
		else{
			passValue(saisie);
		}
	}
}
