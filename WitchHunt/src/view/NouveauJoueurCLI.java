package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import model.bots.BotStrategy;
import model.bots.JoueurVirtuel;
import model.partie.Joueur;
import model.partie.Partie;
import model.partie.playerList;

@SuppressWarnings("deprecation")
public class NouveauJoueurCLI implements CLI, Observer {
	private playerList listeJoueurs;
	private ArrayList<BotStrategy> strats;
	private static String QUITTER = "OK";
	
	
	
	public NouveauJoueurCLI(playerList p, ArrayList<BotStrategy> strats,Partie partie) {
		partie.addObserver(this);
		p.addObserver(this);
		this.listeJoueurs = p;
		this.strats = strats;

	}
	public void action(String saisie) {
		if (saisie.equals("")) {
		} else if (saisie.equals(QUITTER)) {
			if (listeJoueurs.getAllPlayers().size() < 3) {
				System.out.println("Add more players!");
			} else {
				Partie.getInstance().playerSetupDone();
			}
		} else {
			Iterator<BotStrategy> it= strats.iterator();
			BotStrategy chosenStrategy = null;
			while (it.hasNext()) {
				BotStrategy b = it.next();
				if (saisie.equals(b.getStringToType())) {
					chosenStrategy = b;
				}
			}
			synchronized (listeJoueurs) {
				if (listeJoueurs.getAllPlayers().size() < 6) {
					if (chosenStrategy == null) {
						listeJoueurs.addPlayer(new Joueur(saisie));
					} else {
						listeJoueurs.addPlayer(new JoueurVirtuel(Partie.getInstance().randomBotName(), chosenStrategy));
					}
				}
			}
		}
	}
	
	
	@Override
	public void setup() {
		System.out.println("Type the name of the player that you want to add!");
		System.out.println(
				"When you're done and there are more than 3 players added, type " + NouveauJoueurCLI.QUITTER + "\n");
		Iterator<BotStrategy> it = strats.iterator();
		while (it.hasNext()) {
			BotStrategy b = it.next();
			System.out.println("To add a bot with this strategy : ");
			System.out.println(b.toString());
			System.out.println("Type " + b.getStringToType() + "\n");
		}	
	}


	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof playerList) {
			playerList list = (playerList) o;
			if (arg instanceof Joueur) {
				Joueur j = (Joueur) arg;
				System.out.println("New player added : " + j.toString());
			}
			if (list.getAllPlayers().size() == 6) {
				Partie.getInstance().playerSetupDone();
			}
		}
	}
	
}
