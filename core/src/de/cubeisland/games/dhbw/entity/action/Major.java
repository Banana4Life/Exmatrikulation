package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * Major sets if business informatics, business administration or applied informatics is chosen.
 * 0 is business informatics
 * 1 is business administration
 * 2 is applied informatics
 *
 * @author Jonas Dann
 */
public class Major implements CardAction {
    @Override
    public void apply(PlayerCharacter character, int value) {

    }

    @Override
    public void unapply(PlayerCharacter character, int value) {

    }
}
