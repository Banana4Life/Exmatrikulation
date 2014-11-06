package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that lowers the Math skill points.
 *
 * @author Andreas Geis
 */
public class Math implements CardAction {

    public void apply(Character character, int value) {
        character.setMath(character.getMath() + value);
    }

    public void unapply(Character character, int value) {
        character.setMath(character.getMath() - value);
    }

}
