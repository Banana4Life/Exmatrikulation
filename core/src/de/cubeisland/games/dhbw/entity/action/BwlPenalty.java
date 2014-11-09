package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that only decreases the BWL skill by a given value.
 *
 * @author Andreas Geis
 */
public class BwlPenalty implements CardAction {

    public void apply(PlayerCharacter character, int value) {

    }

    public void unapply(PlayerCharacter character, int value) {
        character.setBwl(character.getBwl() - value);
    }

}
