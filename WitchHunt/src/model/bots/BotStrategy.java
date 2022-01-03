package model.bots;

public interface BotStrategy {

	public boolean accuseInsteadOfCard();

	public boolean revealIdentityInsteadOfCard();
	
	public String toString();
	
	public String getStringToType();
}
