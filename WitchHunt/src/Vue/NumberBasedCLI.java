package Vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import partie.Joueur;

public abstract class NumberBasedCLI implements CLI {
	protected int max;
	protected Joueur j;
	protected int min;
	
	protected void ErrorMessage() {
		System.out.println("Please input a number between " + min + " and " + max+".");
	}
	protected abstract void passValue(int nb);
	
	public void action(String saisie) {
		int nb;
		try {
			nb = Integer.parseInt(saisie);
			if(nb>max||nb<min) {
				ErrorMessage();
			}
			else {
				this.passValue(nb-1);
			}
		} catch (NumberFormatException e) {
			ErrorMessage();
		}
	}
	
}
