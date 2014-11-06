package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.*;
import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * Menu sets if the free or story mode is chosen.
 * 0 is story mode
 * 1 is free mode
 * @author Jonas Dann
 */
public class Menu implements CardAction {
    @Override
    public void apply(de.cubeisland.games.dhbw.character.Character character, int value) {
        
    }

    @Override
    public void unapply(Character character, int value) {

    }
}
