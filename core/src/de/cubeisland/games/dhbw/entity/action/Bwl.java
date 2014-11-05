package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.entity.CardAction;
import de.cubeisland.games.dhbw.character.Character;

/**
 * This class represents the action the lowers the BWL skill points.
 *
 * @author Andreas Geis
 */
public class Bwl implements CardAction {

    public void apply(Character c, int value) {
        c.incrementBwl(value);
    }

    public void unapply(Character c, int value) {
        c.decrementBwl(value);
    }

}
