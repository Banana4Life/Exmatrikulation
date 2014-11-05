package de.cubeisland.games.dhbw.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.entity.component.MyCharacter;

/**
 * This class represents an action that rewards the player with a new card
 */
public class CardReward implements CardAction{

	public void apply(MyCharacter c, int cardNumber) {
		// TODO: give the specified card to the player
		// it has to be an item card with the specified cardNumber

	}

	public void unapply(MyCharacter c, int value) {
		// do nothing, since there is no event which removes cards from the hand
	}

}
