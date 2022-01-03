package model.bots;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import model.partie.Carte;
import model.partie.Joueur;
import model.partie.NoCardsToChooseFromException;
import model.partie.NoPlayersToChooseFromException;
import model.partie.Partie;

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
	public boolean isABot() {
		return true;
	}
	
	
	
	
	public String toString() {
		return this.getNomJoueur()+"(bot)";
	}

	@Override
	public String chooseBetween2Options(String a, String b,String AFull,String BFull) {
		Random rd = new Random();
		if(rd.nextBoolean()) {
			return a;
		}
		return b;
	}
	private int askNumber(int min, int max) {
		Random rd = new Random();
		if(min==max) {
			return min;
		}
		int number = rd.nextInt(max + 1 - min) + min;
		System.out.println(number);
		return number;
	}
	
	public Carte choisirCarte(ArrayList<Carte> cartes) throws NoCardsToChooseFromException {
		if(cartes.size()!=0) {
			int number = this.askNumber(1, cartes.size());
			System.out.println(this.toString() + " chose " + cartes.get(number-1).toString());
			System.out.println(cartes.get(number-1).affichageCarte());
			return cartes.get(number-1);
		}
		else {
			throw new NoCardsToChooseFromException(this.toString() + " has no cards to choose from!");
		}
	}

	@Override
	public Joueur choisirJoueur(ArrayList<Joueur> joueurs) throws NoPlayersToChooseFromException {
		if(joueurs.size()!=0) {
			int number = this.askNumber(1, joueurs.size());
			System.out.println(this.toString() + " chose " + joueurs.get(number-1).toString());
			return joueurs.get(number-1);
		}
		else {
			throw new NoPlayersToChooseFromException("You can't choose any players");
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