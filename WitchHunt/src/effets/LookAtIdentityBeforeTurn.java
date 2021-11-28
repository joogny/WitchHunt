package effets;

import partie.Effet;
import partie.Joueur;
import partie.NoPlayersToChooseFromException;
import partie.Partie;

public class LookAtIdentityBeforeTurn extends EffetAvantTour {
	private static final String enoncé = "Choose next player.\nBefore their turn, secretly look at their identity.";
	
	public LookAtIdentityBeforeTurn() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte,Joueur accusateur) {
		// Choose next player
		Joueur j;
		try {
			j = joueurCarte.choisirJoueurNonRevelee();
			Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
			System.out.println(j.toString() + "will take next turn and " + joueurCarte.toString() + " will secretly look at their identity!");
			super.setJoueurCarte(joueurCarte);
			j.ajouterEffetDebutTour(this);
		} catch (NoPlayersToChooseFromException e) {
			System.out.println(e);
		}
	}


	@Override
	public void lancerEffet(Joueur joueurVisé) {
		System.out.println("Because of their last card, " + super.getJoueurCarte()+ " will secretly look at " + joueurVisé.toString() + "'s identity ");
		super.getJoueurCarte().secretlyLookAtIdentity(joueurVisé);
		super.setJoueurCarte(null);
	}
	
}
