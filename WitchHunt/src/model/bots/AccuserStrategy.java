package model.bots;

import java.util.Random;

public class AccuserStrategy implements BotStrategy {

	@Override
	public boolean accuseInsteadOfCard() {
		return true;
	}

	@Override
	public boolean revealIdentityInsteadOfCard() {
		Random rd = new Random();
		return rd.nextBoolean();
	}

	
	public String toString() {
		return "Accuser Strategy : will always try to accuse other players!";
	}

	@Override
	public String getStringToType() {
		return "accuser";
	}
}
