package de.cubeisland.games.dhbw.entity;

import de.cubeisland.engine.reflect.Section;
import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.util.ActionTuple;
import de.cubeisland.games.dhbw.util.Prefab;

import java.util.HashSet;
import java.util.Set;

/**
 * This class specifies the configuration of a card
 *
 * @author Andreas Geis
 * @author Jonas Dann
 */
public class CardPrefab extends Prefab<CardObject> {

    /**
     * The type of the card
     */
    public static enum CardType {
        MENU, EVENT, ITEM
    }

    public static enum SubjectType {
        MATH("Math"),
        BWL("BWL"),
        SOFTSKILL("Soft-Skills"),
        PROGRAMMING("Coding");

        private final String prettyName;

        SubjectType(String prettyName) {
            this.prettyName = prettyName;
        }

        public String getPrettyName() {
            return prettyName;
        }
    }

    public String name;

    public CardType type;

    public String description;

    public Set<ActionTuple> actions = new HashSet<>();

    public class Requirement implements Section {
        public SubjectType subject;
        public int value;

        public boolean passed(PlayerCharacter pc, int diceCount) {
            return value <= pc.get(subject) + diceCount;
        }
    }

    public Requirement requirement;

    public int duration;

    public double rarity;
}
