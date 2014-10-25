package de.cubeisland.games.dhbw.entity;

import de.cubeisland.engine.reflect.Section;
import de.cubeisland.games.dhbw.util.Prefab;

public class CardPrefab extends Prefab {
    public enum CardType {
        EVENT, OBJECT, ABILITY
    }

    // initialization of card variables
    // which attributes are needed depends on the card type, unused attributes get default-values

    public String name;

    // the type of the card
    // public CardType type;
    public String type;

    // the text of the card which gets displayed to the player
    public String description;

    // reward for successfully finishing an event or for equipping an object or ability card
    // e.g. type = math, value = 5
    public class RewardSection implements Section {
        // path will be: reward.section.type
        public String type;

        // path will be: reward.section.value
        public String value;
    }

    // the player failed to complete the event (only for event cards)
    // it is also possible to only fill type (for e.g. de-registration)
    public class PenaltySection implements Section {
        // path will be: penalty.section.type
        public String type;

        // path will be: penalty.section.value
        public String value;
    }

    // the character attribute which is needed to successfully complete an event and the value needed (only for event cards)
    // (e.g. math exam: type = math, value = 20)
    public class ObstacleSection implements Section {
        // path will be: obstacle.section.type
        public String type;

        // path will be: obstacle.section.value
        public String value;
    }

    // a card can be equipped and grants permanent bonuses if equipable = true (only for object and ability cards)
    // otherwise it may only be used once
    public boolean equipable;
}
