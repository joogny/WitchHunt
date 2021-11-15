package partie;

public class JoueurVirtuel extends Joueur {
	private static final String[] randomNames = 
		{"R.O.B","R2-D2","WALL-E","C-18","Terminator","Optimus Prime"};
	public JoueurVirtuel(String nom) {
		super(nom);
	}
	
	public static String getRandomName() {
		int randomNum = (int)(Math.random() * randomNames.length);
		return randomNames[randomNum];
	}

	@Override
	public boolean isABot() {
		return true;
	}
}
;