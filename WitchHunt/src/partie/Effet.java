package partie;

public abstract class Effet {
	private String NomEffet;
	
	public Effet(String nomEffet) {
		this.NomEffet=nomEffet;
	}
	
	public String toString() {
		return NomEffet;
	}
	public abstract void activerEffet(Joueur joueurCarte);

	public void activerEffet(Joueur joueur, Joueur accusateur) {
		//modif
		
	}

	
	
	
}
