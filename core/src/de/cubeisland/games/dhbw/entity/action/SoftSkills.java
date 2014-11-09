package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that increases or lowers the SoftSkill skill points by the same value.
 *
 * @author Andreas Geis
 */
public class SoftSkills implements CardAction {

    public void apply(PlayerCharacter character, int value) {
        character.setSoftSkills(character.getSoftSkills() + value);
    }

    public void unapply(PlayerCharacter character, int value) {
        character.setSoftSkills(character.getSoftSkills() - value);
    }

}
