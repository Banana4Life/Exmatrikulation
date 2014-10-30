package de.cubeisland.games.dhbw.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.entity.component.MyCharacter;

public class Math implements CardAction{

	public void apply(MyCharacter c, int value) {
		c.math += value;
	}

	public void unapply(MyCharacter c, int value) {
		c.math -= value;
	}

}
