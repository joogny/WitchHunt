package effets;

import java.util.ArrayList;
import java.util.Iterator;

import partie.Carte;
import partie.Effet;
import partie.Joueur;
import partie.NoCardsToChooseFromException;
import partie.Partie;

public class TakeRevealedCardFromAnyPlayers extends Effet{
	private static final String enoncé = "Take a revealed Rumour card from any other player into your hand";
	
	public TakeRevealedCardFromAnyPlayers() {
		super(enoncé);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activerEffet(Joueur joueurCarte, Joueur accusateur) {
		ArrayList<Carte> cartes = new ArrayList<>();
		
		Iterator<Carte> it = Partie.getInstance().getCartes().iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if(c.isRevelee()) {
				cartes.add(c);
			}
		}
		if(cartes.size()==0) {
			System.out.println("There aren't any revealed Rumour Cards!");
		}
		else {
			try {
				Carte c = joueurCarte.choisirCarte(cartes);
				Iterator<Joueur> itJoueur = Partie.getInstance().getListeJoueurs().getListeJoueurs().iterator();
				ArrayList<Carte> cartesJoueur;
				
				while(itJoueur.hasNext()) {
					Joueur j = itJoueur.next();
					cartesJoueur=j.getRevealedCards();
					if(cartesJoueur.contains(c)) {
						cartesJoueur.remove(c);
					}
				}
				c.setRevelee(false);
				c.setDefausse(false);
				joueurCarte.addCardToHand(c);
				
				
			} catch (NoCardsToChooseFromException e) {
			System.out.println("There aren't any revealed Rumour Cards!");
			}
		}
	}}
