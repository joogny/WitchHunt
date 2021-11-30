package bots;

import java.util.Random;

public class RandomStrategy implements BotStrategy{
	
	public RandomStrategy() {
		
	}

	@Override
	public boolean accuseInsteadOfCard() {
		Random rd = new Random();
		return rd.nextBoolean();
	}

	@Override
	public boolean revealIdentityInsteadOfCard() {
		Random rd = new Random();
		return rd.nextBoolean();
	}

	
	public String toString() {
		return "Random bot : acts randomly for everything!";
	}
}
