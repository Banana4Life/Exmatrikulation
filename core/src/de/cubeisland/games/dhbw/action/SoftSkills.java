package de.cubeisland.games.dhbw.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.entity.component.MyCharacter;

/**
 * This class represents the action the lowers the soft skill points
 */
public class SoftSkills implements CardAction{

	public void apply(MyCharacter c, int value) {
		c.softSkills += value;
	}

	public void unapply(MyCharacter c, int value) {
		c.softSkills -= value;
	}

}
