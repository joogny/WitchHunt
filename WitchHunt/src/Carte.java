
public class Carte {

	private boolean defausse;
	private String nomCarte;
	private boolean revelee;
	private Carte carteBloquee;
	
	public Carte(String nom) {
		this.nomCarte=nom;
	}
	
	public Carte(Carte c, String nom) {
		this(nom);
		this.carteBloquee=c;
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

	public Carte getCarteBloquee() {
		return carteBloquee;
	}

	public void setCarteBloquee(Carte carteBloquee) {
		this.carteBloquee = carteBloquee;
	}
}
