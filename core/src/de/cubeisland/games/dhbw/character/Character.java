package de.cubeisland.games.dhbw.character;

/**
 * Represents the character in the game holding the skill values.
 *
 * @author Phillip Schichtel
 * @author Andreas Geis
 */
public class Character {

	private int math;
    private int programming;
    private int bwl;
    private int softSkills;

	// TODO set different default values for each course of studies
	public Character() {
		math = 0;
		programming = 0;
		bwl = 0;
		softSkills = 0;
	}

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getProgramming() {
        return programming;
    }

    public void setProgramming(int programming) {
        this.programming = programming;
    }

    public int getBwl() {
        return bwl;
    }

    public void setBwl(int bwl) {
        this.bwl = bwl;
    }

    public int getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(int softSkills) {
        this.softSkills = softSkills;
    }
}
