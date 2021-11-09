
public class Carte {

	private boolean defausse;
	private String nomCarte;
	private boolean revelee;
	private Carte carteBloquante;
	
	public Carte(String nom) {
		this.nomCarte=nom;
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

}
