package model.partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

public class playerList extends Observable{
	private LinkedList<Joueur> listeJoueurs;
	private Joueur accuser;
	private ArrayList<Joueur> listeJoueursElimine;
	
	public playerList() {
		this.listeJoueurs=new LinkedList<>();
		accuser=null;
		this.listeJoueursElimine=new ArrayList<>();
	}
	
	public LinkedList<Joueur> getListeJoueurs() {
		return this.listeJoueurs;
	}
	
	public Joueur getFirstPlayer() {
		return listeJoueurs.getFirst();
	}
	public void reset() {
		listeJoueurs.addAll(listeJoueursElimine);
		listeJoueursElimine = new ArrayList<>();
		Iterator<Joueur> it = listeJoueurs.iterator();
		while(it.hasNext()) {
			it.next().reset();
		}
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
		System.out.println(j.toString() + " will take next turn");
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
	
	public ArrayList<Joueur> getAllPlayers() {
		ArrayList<Joueur> allPlayers = new ArrayList<>();
		allPlayers.addAll(this.listeJoueurs);
		allPlayers.addAll(this.listeJoueursElimine);
		return allPlayers;
	}
	
	public ArrayList<Joueur> sortedListByScore() {
		ArrayList<Joueur> allPlayers=this.getAllPlayers();
		allPlayers.sort(Comparator.comparing(a -> a.getScore()));
		Collections.reverse(allPlayers);
		return allPlayers;
	}
	
	public LinkedList<Joueur> getJoueursNonEliminées() {
		return listeJoueurs;
	}
	
	public LinkedList<Joueur> getJoueursNonRevelées() {
		LinkedList<Joueur> joueursNonRevelées = new LinkedList<>();
		Iterator<Joueur> it = this.listeJoueurs.iterator();
		while(it.hasNext()) {
			Joueur j = it.next();
			if(!j.estRevelee()) {
				joueursNonRevelées.add(j);
			}
		}
		return joueursNonRevelées;
	}
	public void eliminerJoueur(Joueur j) {
		listeJoueurs.remove(j);
		System.out.println(j.toString() + " is eliminated");
		listeJoueursElimine.add(j);
	}
	public void setAccuser(Joueur j) {
		this.accuser=j;
	}

	public void addPlayer(Joueur joueur) {
		this.listeJoueurs.add(joueur);
		this.setChanged();
		this.notifyObservers(joueur);
	}
	public int getPlayerCount() {
		return this.listeJoueurs.size();
	}
	
}
