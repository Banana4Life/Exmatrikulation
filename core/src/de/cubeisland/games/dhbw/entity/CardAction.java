package de.cubeisland.games.dhbw.entity;

import de.cubeisland.games.dhbw.character.Character;

public interface CardAction {

	// if the player successfully completes an event, or uses an item, apply gets called on every action specified in the Card-Config
	// if the player fails to complete an event, or an item effect wears off, unapply gets called on every action
	void apply(Character c, int value);

	void unapply(Character c, int value);
}
