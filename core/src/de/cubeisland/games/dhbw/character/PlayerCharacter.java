package de.cubeisland.games.dhbw.character;

import de.cubeisland.games.dhbw.entity.CardPrefab;

/**
 * Represents the character in the game holding the skill values.
 *
 * @author Phillip Schichtel
 * @author Andreas Geis
 */
public class PlayerCharacter {

    private int math;
    private int programming;
    private int bwl;
    private int softSkills;

    // TODO set different default values for each course of studies
    public PlayerCharacter() {
        this.math = 10;
        this.programming = 10;
        this.bwl = 10;
        this.softSkills = 10;
    }

    public int getMath() {
        return math;
    }

    public PlayerCharacter setMath(int math) {
        this.math = math;
        return this;
    }

    public int getProgramming() {
        return programming;
    }

    public PlayerCharacter setProgramming(int programming) {
        this.programming = programming;
        return this;
    }

    public int getBwl() {
        return bwl;
    }

    public PlayerCharacter setBwl(int bwl) {
        this.bwl = bwl;
        return this;
    }

    public int getSoftSkills() {
        return softSkills;
    }

    public PlayerCharacter setSoftSkills(int softSkills) {
        this.softSkills = softSkills;
        return this;
    }

    public int get(CardPrefab.SubjectType type) {
        switch(type) {
            case BWL: return bwl;
            case PROGRAMMING: return programming;
            case MATH: return math;
            case SOFTSKILL: return softSkills;
        }
        return -1;
    }
}
