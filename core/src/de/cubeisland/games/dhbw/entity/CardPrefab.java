package de.cubeisland.games.dhbw.entity;

import de.cubeisland.engine.reflect.Section;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.util.Prefab;

import javax.swing.*;
import java.util.Map;

public class CardPrefab extends Prefab<CardObject> {

    public enum CardType {
        MENU, EVENT, ITEM
    }

	// initialization of card variables
	// which attributes are needed depends on the card type, unused attributes get default-values

	public String name;

	// the type of the card
	public CardType type;

	// the text of the card which gets displayed to the player
	public String description;

	// holds all possible actions for the card:
	// rewards and penalties from events, as well as stat bonuses for using items
    public Map<Class<? extends Action>, Integer> actions;

	// the character attribute which is needed to successfully complete an event and the value needed (only for event cards)
	// (e.g. math exam: type = math, value = 20)
	public class Requirement implements Section {
		// path will be: requirement.section.subject
		public String subject;

		// path will be: requirement.section.value
		public String value;
	}

	// how many rounds you get the card bonus (only for item cards)
	public int duration;

	// how often a card appears in the game
	public double rarity;
}
