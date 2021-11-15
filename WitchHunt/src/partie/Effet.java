package partie;

public abstract class Effet {
	private String NomEffet;
	private boolean needCarteRevelee;
	private boolean needVillageoisRevelee;
	
	public Effet(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		NomEffet = nomEffet;
		this.needCarteRevelee = needCarteRevelee;
		this.needVillageoisRevelee = needVillageoisRevelee;
	}

	public Effet(String nomEffet) {
		this(nomEffet,false,false);
	}
	
	
	public abstract boolean estJouable();
	
	public abstract void activerEffet(Joueur joueurCarte);
	
	
	
}
