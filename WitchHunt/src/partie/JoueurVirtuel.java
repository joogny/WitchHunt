package partie;

import java.util.Random;
import java.util.Scanner;

public class JoueurVirtuel extends Joueur {
	private static final String[] randomNames = 
		{"R.O.B","R2-D2","WALL-E","C-18","Terminator","Optimus Prime"};
	public JoueurVirtuel(String nom) {
		super(nom);
	}
	
	public static String getRandomName() {
		int randomNum = (int)(Math.random() * randomNames.length);
		return randomNames[randomNum];
	}

	@Override
	public void discoverHand() {
		System.out.println(this.getNomJoueur() + " discovered their hand...");
	}
	@Override
	public void chooseIdentityCard() {
		Random r = new Random();
		this.setEstSorciere(r.nextBoolean());
		System.out.println(this.getNomJoueur() + " chose their role!");
	}
	@Override
	public boolean isABot() {
		return true;
	}
	public String toString() {
		return this.getNomJoueur()+"(bot)";
	}
}
;