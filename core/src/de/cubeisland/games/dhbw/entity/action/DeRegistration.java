package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.character.Character;

/**
 * This class represents an action that de-registrates the player
 *
 * @author Andreas Geis
 */
public class DeRegistration implements CardAction{

	public void apply(Character c, int value) {
		// do nothing, since the player successfully completed the event

	}

	public void unapply(Character c, int value) {
		// TODO: de-register the player --> end the game and show a GameOver-Screen

	}

}
