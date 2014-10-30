package de.cubeisland.games.dhbw.entity;

import de.cubeisland.games.dhbw.entity.component.MyCharacter;

public interface CardAction {

	// if the player successfully completes an event, or uses an item, apply gets called on every action specified in the Card-Config
	// if the player fails to complete an event, or an item effect wears off, unapply gets called on every action
	void apply(MyCharacter c, int value);

	void unapply(MyCharacter c, int value);
}
