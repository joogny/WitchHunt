package Vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import partie.Carte;
import partie.Joueur;


public class AskNumberCLI implements Runnable{
		private int min;
		private int max;
		private Joueur j;
		private boolean stop;
	public AskNumberCLI(int min,int max,Joueur j,CartesCLI CLI) {
		this.min=min;
		this.j=j;
		this.max=max;
		this.stop=false;
		Thread t = new Thread(this);
		t.start();
	}
	public void stop() {
		this.stop=true;
	}
	@Override
	public void run() {
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
					
					this.stop();
				}
			} catch (NumberFormatException e) {
				System.out.println(ErrorMessage);
			}
		}
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
