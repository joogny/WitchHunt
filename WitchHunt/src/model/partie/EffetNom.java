package model.partie;

import model.effets.*;

public enum EffetNom {
	TAKENEXTTURN(new TakeNextTurn()),
	REVEALANIDENTITY(new RevealAnIdentity()), 
	DISCARDCARDFROMHAND(new DiscardCardFromHand()), 
	LOOKATIDENTITYBEFORETURN(new LookAtIdentityBeforeTurn()), 
	TAKEREVEALEDCARDTOHAND(new TakeRevealedCardToHand()), 
	CHOOSENEXTPLAYER(new ChooseNextPlayer()),
	TAKECARDFROMACCUSER(new TakeCardFromAccuser()),
	TAKECARDFROMHANDBEFORETURN(new TakeCardFromHandBeforeTurn()),
	REVEALORDISCARD(new RevealOrDiscard()),
	ACCUSERDISCARDS(new AccuserDiscards()),
	REVEALYOURIDENTITY(new RevealYourIdentity()), 
	CANTREACCUSE(new CantReaccuse()),
	TAKEREVEALEDCARDFROMANYPLAYERS(new TakeRevealedCardFromAnyPlayers()),
	ADDDISCARDEDCARDTOHAND(new AddDiscardedCardToHand());
	
	private Effet effet;
	EffetNom(Effet e) {
		this.effet=e;
	}
	

	public Effet getEffet() {
		return effet;
	}
}
