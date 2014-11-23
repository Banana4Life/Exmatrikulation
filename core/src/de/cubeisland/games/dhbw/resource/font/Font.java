package de.cubeisland.games.dhbw.resource.font;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * This class wraps a free type font generator in an immutable way.
 * Changing the properties of the Wrap will result in a new Font instance.
 *
 * @author Phillip Schichtel
 */
public class Font {
    private final FreeTypeFontGenerator generator;
    private final boolean flipped;
    private final SizeDefinition size;
    private BitmapFont bitmapFont;
    private Font flippedVersion;

    protected Font(FreeTypeFontGenerator generator, boolean flipped, SizeDefinition size) {
        this.generator = generator;
        this.flipped = flipped;
        this.size = size;
        this.bitmapFont = null;
    }

    public Font(FreeTypeFontGenerator generator, boolean flipped, int size) {
        this(generator, flipped, new StaticSize(size));
    }

    public boolean isFlipped() {
        return this.flipped;
    }

    public Font flipped() {
        if (this.flippedVersion == null) {
            this.flippedVersion = new Font(this.generator, !this.flipped, this.size);
            this.flippedVersion.flippedVersion = this;
        }
        return this.flippedVersion;
    }

    public int getSize() {
        return this.size.getSize();
    }

    public Font setSize(int size) {
        return new Font(this.generator, this.flipped, size);
    }

    public BitmapFont getBitmapFont() {
        if (this.bitmapFont == null) {
            invalidate();
            FreeTypeFontParameter param = new FreeTypeFontParameter();
            param.size = this.getSize();
            param.flip = this.flipped;
            this.bitmapFont = this.generator.generateFont(param);
        }
        return this.bitmapFont;
    }

    public void invalidate() {
        if (this.bitmapFont != null) {
            this.bitmapFont.dispose();
            this.bitmapFont = null;
        }
    }

    public Font copy() {
        return new Font(this.generator, this.flipped, this.size);
    }
}
