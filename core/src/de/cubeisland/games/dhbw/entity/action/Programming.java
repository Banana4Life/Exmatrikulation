package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action the lowers the powering skill points.
 *
 * @author Andreas Geis
 */
public class Programming implements CardAction {

    public void apply(Character character, int value) {
        character.setProgramming(character.getProgramming() + value);
    }

    public void unapply(Character character, int value) {
        character.setProgramming(character.getProgramming() - value);
    }

}
