package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.character.Character;

/**
 * This class represents the action the lowers the soft skill points.
 *
 * @author Andreas Geis
 */
public class SoftSkills implements CardAction{

	public void apply(Character c, int value) {
		c.softSkills += value;
	}

	public void unapply(Character c, int value) {
		c.softSkills -= value;
	}

}
