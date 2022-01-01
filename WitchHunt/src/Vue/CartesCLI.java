package Vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import partie.Carte;
import partie.Joueur;

public class CartesCLI extends NumberBasedCLI{
	private ArrayList<Carte> cartes;

	
	public CartesCLI(Joueur j, ArrayList<Carte> cartes) {
		this.cartes=cartes;
		this.j=j;
		this.max=cartes.size();
		this.min=1;
	}
	@Override
	protected void passValue(int nb) {
		j.setChosenCard(cartes.get(nb));
	}
	@Override
	public void setup() {
		this.ErrorMessage();
		Iterator<Carte> it = cartes.iterator();
		int index=1;
		while(it.hasNext()) {
			System.out.println(index+" : ");
			System.out.println(it.next().affichageCarte());
			System.out.println();
			index++;
		}
	}
	
	
	
}
