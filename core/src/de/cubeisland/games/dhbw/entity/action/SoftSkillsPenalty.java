package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents the action that only decreases the SoftSkills skill by a given value.
 *
 * @author Andreas Geis
 */
public class SoftSkillsPenalty implements CardAction {

    public void apply(Character character, int value) {

    }

    public void unapply(Character character, int value) {
        character.setSoftSkills(character.getSoftSkills() - value);
    }

}
