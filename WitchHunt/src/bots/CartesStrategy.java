package bots;

public class CartesStrategy implements BotStrategy {
	public boolean accuseInsteadOfCard() {
		return false;
	}

	public boolean revealIdentityInsteadOfCard() {
		return false;
	}
	
	public String toString() {
		return "Cartes Strategy : will always try to play cards!";
	}

	@Override
	public String getStringToType() {
		return "cartes";
	}
}