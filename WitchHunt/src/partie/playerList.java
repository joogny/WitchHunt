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
	public Joueur getFirstPlayer() {
		return listeJoueurs.getFirst();
	}
	public void movePlayerLast(Joueur j ) {
		Iterator<Joueur> it = listeJoueurs.iterator();
		boolean playerFound = false;
		while(it.hasNext()&&!playerFound) {
			if(j.equals(it.next())) {
				it.remove();
				listeJoueurs.add(j);
				playerFound = true;
			}
		}
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
	
	
	
	public LinkedList<Joueur> getJoueursNonEliminées() {
		LinkedList<Joueur> list = new LinkedList<>();
		Iterator<Joueur> it = listeJoueurs.iterator();
		while(it.hasNext()) {
			Joueur j = it.next();
			if(!j.estEliminee()) {
				list.add(j);
			}
		}
		return list;
	}
	public void setAccuser(Joueur j) {
		this.accuser=j;
	}

	public void addPlayer(Joueur joueur) {
		this.listeJoueurs.add(joueur);
	}
	public int getPlayerCount() {
		return this.listeJoueurs.size();
	}
}
