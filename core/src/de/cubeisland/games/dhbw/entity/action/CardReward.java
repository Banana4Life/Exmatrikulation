package de.cubeisland.games.dhbw.entity.action;

import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardAction;

/**
 * This class represents an action that rewards the player with a new card.
 *
 * @author Andreas Geis
 */
public class CardReward implements CardAction {

    public void apply(PlayerCharacter character, int cardNumber) {
        // give the specified card to the player
        // it has to be an item card with the specified cardNumber

    }

    public void unapply(PlayerCharacter character, int value) {
        // do nothing, since there is no event which removes cards from the hand
    }

}
