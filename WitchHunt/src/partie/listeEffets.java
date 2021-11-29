package partie;

import java.util.ArrayList;
import java.util.Iterator;

public class listeEffets {
	private ArrayList<Effet> effets;
	private boolean needRevealedCard;
	private boolean needRevealedVillager;
	public listeEffets() {
		effets = new ArrayList<>();
	}
	
	public listeEffets(boolean needRevealedCard, boolean needRevealedVillager) {
		this();
		this.needRevealedVillager=needRevealedVillager;
		this.needRevealedCard=needRevealedCard;
	}
	
	public boolean estJouable(Joueur j) {
		if(this.needRevealedCard) {
			boolean carteRevelee =false;
			Iterator<Carte> it = j.getMain().iterator();
			while(it.hasNext()) {
				if(it.next().isRevelee()) {
					carteRevelee=true;
				}
			}
			if(!carteRevelee) {
				return false;
			}
		}
		if(this.needRevealedVillager) {
			if(!j.estRevelee()) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean needRevealedCard() {
		return this.needRevealedCard;
	}
	
	public boolean needRevealedVillager() {
		return this.needRevealedVillager;
	}
	public void ajouterEffet(EffetNom e) {
		effets.add(e.getEffet());
	}
	public ArrayList<Effet> getEffets() {
		return this.effets;
	}
}
