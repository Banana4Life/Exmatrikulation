package de.cubeisland.games.dhbw.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.entity.component.MyCharacter;

/**
 * This class represents the action the lowers the BWL skill points
 */
public class Bwl implements CardAction{

	public void apply(MyCharacter c, int value) {
		c.bwl += value;
	}

	public void unapply(MyCharacter c, int value) {
		c.bwl -= value;
	}

}
