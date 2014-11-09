package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * Menu sets if the free or story mode is chosen.
 * 0 is story mode
 * 1 is free mode
 *
 * @author Jonas Dann
 */
public class Menu implements CardAction {
    @Override
    public void apply(PlayerCharacter character, int value) {

    }

    @Override
    public void unapply(PlayerCharacter character, int value) {

    }
}
