package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that lowers the SoftSkill points.
 *
 * @author Andreas Geis
 */
public class SoftSkills implements CardAction {

    public void apply(Character character, int value) {
        character.setSoftSkills(character.getSoftSkills() + value);
    }

    public void unapply(Character character, int value) {
        character.setSoftSkills(character.getSoftSkills() - value);
    }

}
