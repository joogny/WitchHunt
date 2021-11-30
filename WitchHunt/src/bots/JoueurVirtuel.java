package bots;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import partie.Carte;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.NoPlayersToChooseFromException;
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
	
	

	
	public String toString() {
		return this.getNomJoueur()+"(bot)";
	}

	@Override
	public String chooseBetween2Options(String a, String b) {
		Random rd = new Random();
		if(rd.nextBoolean()) {
			return a;
		}
		return b;
	}
	@Override
	public int askNumber(int min, int max) {
		Random rd = new Random();
		if(min==max) {
			return min;
		}
		return rd.nextInt(max + 1 - min) + min;
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
	@Override
	protected void accuseOrUseCard() throws NoPlayersToChooseFromException, NoCardsToChooseFromException {
		if(strategy.accuseInsteadOfCard()) {
			System.out.println(this.toString() + " decided to accuse someone\n");
			super.accuser();
		}
		else {
			System.out.println(this.toString() + " decided to play a card\n");
			super.playHuntCard();
		}
	}
	
	
	@Override
	protected void revealIdentityOrUseCard(Joueur accusateur) throws NoCardsToChooseFromException {
		if(super.estSorciere()) {
			System.out.println(this.toString() + " decided to play a card\n");
			super.playWitchCard(accusateur);
		}
		else {
			if(strategy.revealIdentityInsteadOfCard()) {
				System.out.println(this.toString() + " decided to reveal their identity.\n");
				this.revelerIdentite(accusateur);
			}
			else {
				System.out.println(this.toString() + " decided to play a card\n");
				super.playWitchCard(accusateur);	
			}
		}
		
	}

	public void secretlyLookAtIdentity(Joueur joueurVisé) {
		System.out.println("\n \n" + this.toString() + "secretly looked at "+ joueurVisé.getNomJoueur() + "'s identity!");
	}
}