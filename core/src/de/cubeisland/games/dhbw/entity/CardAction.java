package de.cubeisland.games.dhbw.entity;

import de.cubeisland.games.dhbw.character.PlayerCharacter;

/**
 * These actions are applied to the character depending on the type of card.
 * Events will unapply a value when the player fails it or apply them when he is successful.
 * Items will apply their value upon usage and unapply it when removed.
 *
 * @author Phillip Schichtel
 * @author Andreas Geis
 */
public interface CardAction {

    /**
     * This method applies the action value to a character
     *
     * @param character the character
     * @param value     the value to apply
     */
    void apply(PlayerCharacter character, int value);

    /**
     * This method reverses the applied value
     *
     * @param character the character
     * @param value     the value to unapply
     */
    void unapply(PlayerCharacter character, int value);
}
