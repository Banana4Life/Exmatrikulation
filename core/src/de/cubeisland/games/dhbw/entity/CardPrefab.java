package de.cubeisland.games.dhbw.entity;

import de.cubeisland.engine.reflect.Section;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.util.Prefab;

import javax.swing.*;
import java.util.Map;

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
        MATH, BWL, SOFTSKILL, PROGRAMMING
    }

    public String name;

    public CardType type;

    public String description;

    public Map<Class<? extends Action>, Integer> actions;

    public class Requirement implements Section {
        public SubjectType subject;
        public int value;
    }

    public Requirement requirement;

    public int duration;

    public double rarity;
}
