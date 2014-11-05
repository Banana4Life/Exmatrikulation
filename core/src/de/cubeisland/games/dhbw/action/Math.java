package de.cubeisland.games.dhbw.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.entity.component.MyCharacter;

/**
 * This class represents the action the lowers the math skill points
 */
public class Math implements CardAction{

	public void apply(MyCharacter c, int value) {
		c.math += value;
	}

	public void unapply(MyCharacter c, int value) {
		c.math -= value;
	}

}
