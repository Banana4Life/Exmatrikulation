package de.cubeisland.games.dhbw.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.entity.component.MyCharacter;

public class DeRegistration implements CardAction{

	public void apply(MyCharacter c, int value) {
		// do nothing, since the player successfully completed the event

	}

	public void unapply(MyCharacter c, int value) {
		// TODO: de-register the player --> end the game and show a GameOver-Screen

	}

}
