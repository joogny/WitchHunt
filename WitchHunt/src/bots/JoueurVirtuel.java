package bots;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import partie.Carte;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.Partie;

public class JoueurVirtuel extends Joueur {
	BotStrategy strategy;
	public JoueurVirtuel(String nom,BotStrategy strat) {
		super(nom);
		this.strategy = strat;
	}
	
	private static final String[] randomNames = 
		{"R.O.B","R2-D2","WALL-E","C-18","Terminator","Optimus Prime"};
	
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
	
	
	@Override
	public int askNumber(int min, int max) {
		return strategy.askNumber(min,max);
	}

	
	public String toString() {
		return this.getNomJoueur()+"(bot)";
	}

	public String chooseBetween2Options(String a, String b) {
		return strategy.chooseBetween2Options(a, b);
	}
	
	public Carte choisirCarte(ArrayList<Carte> cartes) throws NoCardsToChooseFromException {
		if(cartes.size()!=0) {
			int number = this.askNumber(1, cartes.size());
			System.out.println(this.toString() + " chose \n" + cartes.get(number-1).toString());
			return cartes.get(number-1);
		}
		else {
			throw new NoCardsToChooseFromException(this.toString() + " has no cards to choose from!");
		}
	}
	
	
	public void secretlyLookAtIdentity(Joueur joueurVisé) {
		System.out.println("\n \n" + this.toString() + "secretly looked at "+ joueurVisé.getNomJoueur() + "'s identity!");
	}
}