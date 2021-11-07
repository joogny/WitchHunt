
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
}
