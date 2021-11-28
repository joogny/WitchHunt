package effets;

import partie.Effet;
import partie.Joueur;
import partie.NoPlayersToChooseFromException;
import partie.Partie;

public class LookAtIdentityBeforeTurn extends EffetAvantTour {
	private static final String enonc� = "Choose next player.\nBefore their turn, secretly look at their identity.";
	
	public LookAtIdentityBeforeTurn() {
		super(enonc�);
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
	public void lancerEffet(Joueur joueurVis�) {
		System.out.println("Because of their last card, " + super.getJoueurCarte()+ " will secretly look at " + joueurVis�.toString() + "'s identity ");
		super.getJoueurCarte().secretlyLookAtIdentity(joueurVis�);
		super.setJoueurCarte(null);
	}
	
}
