package VueTest;

import java.util.ArrayList;
import java.util.Iterator;
import partie.Carte;
import partie.Joueur;


public class CartesCLI implements Runnable{
	private Joueur joueur;
	private ArrayList<Carte> cartes;
	
	public CartesCLI(Joueur j,ArrayList<Carte> cartes) {
		this.joueur=j;
		this.cartes=cartes;
		Thread t = new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		Iterator<Carte> it = cartes.iterator();
		int index=0;
		while(it.hasNext()) {
			index++;
			Carte c  = it.next();
			System.out.println(index+" : \n" + c.affichageCarte());
		}
		int number = joueur.askNumber(1, index);
		joueur.setChosenCard(cartes.get(number-1));
	}	
}
