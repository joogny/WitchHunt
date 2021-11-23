package partie;

public class Carte {

	private boolean defausse;
	private String nomCarte;
	private boolean revelee;
	private Carte carteBloquante;
	private Effet[] effetsWitch;
	private Effet[] effetsHunt;
	public Carte(String nom) {
		this.nomCarte=nom;
		this.effetsWitch= new Effet[0];
		this.effetsHunt = new Effet[0];
	}
	
	public Carte(String nom, Carte c) {
		this(nom);
		this.carteBloquante=c;
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


	public boolean isRevelee() {
		return revelee;
	}

	public void setRevelee(boolean revelee) {
		this.revelee = revelee;
	}

	public Carte getCarteBloquante() {
		return carteBloquante;
	}
	public void activerEffets(Joueur j,Effet[] effets) {
		for(Effet e : effetsWitch) {
			if(e.estJouable()) {
				e.activerEffet(j);
			}
		}
	}
	public void activerEffetHunt(Joueur j) {
		activerEffets(j,this.effetsHunt);
	}
	public void activerEffetWitch(Joueur j) {
		activerEffets(j,this.effetsWitch);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("**************************"+"\n");
		sb.append(this.nomCarte+"\n");
		sb.append("\n"+"Witch?"+"\n");
		for(Effet e :effetsWitch) {
			sb.append(e.toString()+"\n");
		}
		sb.append("\n"+"Hunt!"+"\n");
		for(Effet e : effetsHunt) {
			sb.append(e.toString()+"\n");
		}
		sb.append("**************************"+"\n");
		return sb.toString();
	}
	

}
