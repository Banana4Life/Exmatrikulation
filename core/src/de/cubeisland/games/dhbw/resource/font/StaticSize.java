package de.cubeisland.games.dhbw.resource.font;

/**
 * This class represents a static size definition for a font.
 *
 * @author Phillip Schichtel
 */
public class StaticSize implements SizeDefinition {
    private final int size;

    public StaticSize(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
