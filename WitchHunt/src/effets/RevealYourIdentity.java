package effets;

import partie.Effet;
import partie.Joueur;

public class RevealYourIdentity extends Effet {
	private final static String enonce = "Reveal your identity."
			+"\nWitch: Player to your left takes next turn" 
			+"\nVillager: choose next player.";
	public RevealYourIdentity() {
		super(enonce);
	}

	@Override
	public void activerEffet(Joueur joueurCarte, Joueur accusateur) {
		joueurCarte.revelerIdentite();
		Effet e = new ChooseNextPlayer();
		if(joueurCarte.estSorciere()) {
			System.out.println("Who's on your left?");
			e.activerEffet(joueurCarte, accusateur);
			//à modif?
		}
		else {
			e.activerEffet(joueurCarte, accusateur);
		}

	}

}
