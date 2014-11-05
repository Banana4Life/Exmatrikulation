package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.character.Character;

/**
 * This class represents the action the lowers the powering skill points.
 *
 * @author Andreas Geis
 */
public class Programming implements CardAction {

    public void apply(Character c, int value) {
        c.incrementProgramming(value);
    }

    public void unapply(Character c, int value) {
        c.decrementProgramming(value);
    }

}
