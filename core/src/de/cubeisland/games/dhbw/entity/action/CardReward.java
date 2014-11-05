package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.Character;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents an action that rewards the player with a new card.
 *
 * @author Andreas Geis
 */
public class CardReward implements CardAction {

    public void apply(Character character, int cardNumber) {
        // TODO: give the specified card to the player
        // it has to be an item card with the specified cardNumber

    }

    public void unapply(Character character, int value) {
        // do nothing, since there is no event which removes cards from the hand
    }

}
