package partie;

public abstract class Effet {
	private String NomEffet;
	
	public Effet(String nomEffet, boolean needCarteRevelee, boolean needVillageoisRevelee) {
		NomEffet = nomEffet;
	}
	
	public Effet(String nomEffet) {
		this(nomEffet,false,false);
	}
	
	public String toString() {
		return NomEffet;
	}
	public abstract void activerEffet(Joueur joueurCarte);

	public void activerEffet(Joueur joueur, Joueur accusateur) {
		//modif
		
	}

	
	
	
}
