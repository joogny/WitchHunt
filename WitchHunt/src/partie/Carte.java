package partie;

import java.util.Iterator;

public class Carte {

	private boolean defausse;
	private String nomCarte;
	private boolean revelee;
	private Carte carteBloquante;
	private listeEffets effetsWitch;
	private listeEffets effetsHunt;
	private String filePath;
	
	public Carte(String nom) {
		this.nomCarte=nom;
		this.effetsWitch= new listeEffets();
		this.effetsHunt = new listeEffets();
	}
	
	public Carte(String nom,listeEffets witch, listeEffets hunt) {
		this(nom);
		this.effetsWitch=witch;
		this.effetsHunt=hunt;
	}
	
	public Carte(String nom, Carte c) {
		this(nom);
		this.carteBloquante=c;
	}
	

	public Carte(String nom, String filePath) {
		this(nom);
		this.filePath=filePath;
	}

	//setters and getters 
	public boolean isDefausse() {
		return defausse;
	}

	public void setDefausse(boolean defausse) {
		this.defausse = defausse;
	}

	public String getNomCarte() {
		return nomCarte;
	}
	
	public boolean estJouable() {
		return !this.defausse && !this.revelee;
	}
	public boolean estJouableWitch(Joueur j ) {
		if(!estJouable()) {
			return false;
		}
		return this.effetsWitch.estJouable(j);
	}
	
	public boolean estJouableHunt(Joueur j ) {
		if(!estJouable()) {
			return false;
		}
		return this.effetsHunt.estJouable(j);
	}

	public boolean isRevelee() {
		return revelee;
	}

	public void setRevelee(boolean revelee) {
		this.revelee = revelee;
	}

	public Carte getCarteBloquante() {
		return carteBloquante;
	}
	
	public String toString() {
		/*StringBuffer sb = new StringBuffer();
		for(int i=0;i<80;i++) {
			sb.append("*");
		}
		sb.append("\n");
		sb.append(this.nomCarte+"\n");
		sb.append("\n"+"Witch?");
		if(effetsWitch.needRevealedCard()) {
			sb.append(" (Only playable if you have a revealed Rumour Card.)");
		}
		if(effetsWitch.needRevealedVillager()) {
			sb.append(" (Only playable if you have been revealed as a Villager.)");
		}
		sb.append("\n");
		for(Effet e :effetsWitch.getEffets()) {
			sb.append(e.toString()+"\n");
		}
		sb.append("\n"+"Hunt!");
		if(effetsHunt.needRevealedCard()) {
			sb.append(" (Only playable if you have a revealed Rumour Card.)");
		}
		if(effetsHunt.needRevealedVillager()) {
			sb.append(" (Only playable if you have been revealed as a Villager.)");
		}
		sb.append("\n");
		for(Effet e : effetsHunt.getEffets()) {
			sb.append(e.toString()+"\n");
		}
		for(int i=0;i<80;i++) {
			sb.append("*");
		}
		sb.append("\n");
		return sb.toString();*/
		return this.nomCarte;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Carte) {
			Carte c = (Carte) o;
			if(!(c.nomCarte==this.nomCarte)) {
				return false;
			}
			return (this.effetsHunt==c.effetsHunt && this.effetsWitch == c.effetsWitch);
			
		}
		return false;
	}

	public void activerEffetWitch(Joueur joueur, Joueur accusateur) {
		for(Effet e : effetsWitch.getEffets()) {
			e.activerEffet(joueur,accusateur); //
		}
	}

	
	
	public void activerEffetHunt(Joueur joueur) {
		for(Effet e : effetsHunt.getEffets()) {
			e.activerEffet(joueur,null); //
		}
		
	}
	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
