package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.character.Character;

/**
 * This class represents the action the lowers the math skill points.
 *
 * @author Andreas Geis
 */
public class Math implements CardAction {

    public void apply(Character c, int value) {
        c.incrementMath(value);
    }

    public void unapply(Character c, int value) {
        c.decrementMath(value);
    }

}
