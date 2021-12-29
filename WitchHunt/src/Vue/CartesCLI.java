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

public class CartesCLI implements Runnable {
	private ArrayList<Carte> cartes;
	private Joueur j;
	private boolean stop;
	
	public CartesCLI(Joueur j, ArrayList<Carte> cartes) {
		this.cartes=cartes;
		this.j=j;
		this.stop=false;
		Thread t = new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		Iterator<Carte> it = cartes.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().affichageCarte());
		}
		int min=1;
		int max=cartes.size();
		String ErrorMessage = "Please input a number between " + min + " and " + max+".";
		System.out.println(ErrorMessage);
		String saisie = null;
		int nb;
		while(!stop) {
			saisie = lireChaine();
			try {
				nb = Integer.parseInt(saisie);
				if(nb>max||nb<min) {
					System.out.println(ErrorMessage);
				}
				else {
					j.setChosenCard(cartes.get(nb-1));
					this.stop();
				}
			} catch (NumberFormatException e) {
				System.out.println(ErrorMessage);
			}
		}

	}
	
	private void stop() {
		this.stop=true;
	}
	private String lireChaine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String resultat = null;
		try {
			resultat = br.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return resultat;
	}
	
	
}
