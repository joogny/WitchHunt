package partie;

import java.util.Iterator;
import java.util.LinkedList;

public class playerList {
	private LinkedList<Joueur> listeJoueurs;
	private Joueur accuser;
	public playerList() {
		this.listeJoueurs=new LinkedList<>();
		accuser=null;
	}
	
	public LinkedList<Joueur> getListeJoueurs() {
		return this.listeJoueurs;
	}
	
	public void movePlayerFirst(Joueur j) {
		Iterator<Joueur> it = listeJoueurs.iterator();
		boolean playerFound = false;
		while(it.hasNext()&&!playerFound) {
			if(j.equals(it.next())) {
				it.remove();
				listeJoueurs.addFirst(j);
				playerFound = true;
			}
		}
	}

	public Joueur getAccuser() {
		return accuser;
	}
	public void setAccuser(Joueur j) {
		this.accuser=j;
	}
}
