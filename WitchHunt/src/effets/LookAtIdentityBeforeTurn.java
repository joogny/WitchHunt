package effets;

import partie.Effet;
import partie.Joueur;
import partie.Partie;

public class LookAtIdentityBeforeTurn extends EffetAvantTour {
	private static final String enoncé = "Before their turn, secretly look at their identity.";
	
	public LookAtIdentityBeforeTurn() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void activerEffet(Joueur joueurCarte) {
		// Choose next player
		Joueur j = joueurCarte.choisirJoueur();
		Partie.getInstance().getListeJoueurs().movePlayerFirst(j);
		if(j.estRevelee()) {
			System.out.println("We already know " + j.toString() + "'s role!");
		}
		else {
			j.ajouterEffetDebutTour(this);
		}
	}


	@Override
	public void lancerEffet(Joueur joueurVisé) {
		System.out.println(super.getJoueurCarte()+ " will secretly look at " + joueurVisé.toString() + "'s identity ");
		super.getJoueurCarte().secretlyLookAtIdentity(joueurVisé);
	}
	
}
