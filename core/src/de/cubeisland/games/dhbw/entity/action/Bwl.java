package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that increases or lowers the BWL skill points by the same value.
 *
 * @author Andreas Geis
 */
public class Bwl implements CardAction {

    public void apply(PlayerCharacter character, int value) {
        character.setBwl(character.getBwl() + value);
    }

    public void unapply(PlayerCharacter character, int value) {
        character.setBwl(character.getBwl() - value);
    }

}
