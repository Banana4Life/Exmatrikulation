package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that only increases the Programming skill by a given value.
 *
 * @author Andreas Geis
 */
public class ProgrammingReward implements CardAction {

    public void apply(Character character, int value) {
        character.setProgramming(character.getProgramming() + value);
    }

    public void unapply(Character character, int value) {

    }

}
