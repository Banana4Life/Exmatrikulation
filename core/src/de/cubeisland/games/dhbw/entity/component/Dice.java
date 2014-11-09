package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;

import java.util.Random;

/**
 * Dice manages the face of the dice.
 * @author Jonas Dann
 */
public class Dice extends Component {
    private int count = 1;
    private int ticks = 0;

    /**
     * Updates the count of the dice and decreases the ticks that the dice is still rolling
     * @return Return this;
     */
    public Dice update() {
        if (ticks > 0) {
            count = new Random().nextInt(20) + 1;
            ticks--;
        }
        return this;
    }

    /**
     * Gets the current face of the dice.
     * @return Returns the number.
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the ticks the dice is still rolling.
     * @return Returns the ticks.
     */
    public int getTicks() {
        return ticks;
    }

    /**
     * Sets the ticks the dice is rolling.
     * @param ticks The number of ticks to use.
     * @return Returns this.
     */
    public Dice setTicks(int ticks) {
        this.ticks = ticks;
        return this;
    }
}
