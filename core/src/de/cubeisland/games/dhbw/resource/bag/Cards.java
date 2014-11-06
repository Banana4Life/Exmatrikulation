package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.component.Card;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all the card prefabs.
 */
public class Cards extends ResourceBag<Card> {
    private Reflector reflector;
    private Pixmap frontTemplate;
    private final BitmapFont font;

    public Card dummy;

    //menu cards
    public Card menustorymode;
    public Card menufreemode;
    public Card menumultimode;

    // initialization of all cards; name must be equal to name of the config-file
    // event cards
    public Card mathePrüfung;

    // item cards
    public Card wasserflasche1;
    public Card wasserflasche2;

    public Cards(Reflector reflector) {
        this.reflector = reflector;

        this.frontTemplate = new Pixmap(Gdx.files.internal("cards/cardfront.png"));
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.setScale(.25f, -.25f);
    }

    @Override
    protected Card load(FileRef basedir, Field field) {
        CardPrefab prefab = this.reflector.load(CardPrefab.class, basedir.child(field.getName()+".yml").getInputStream());

        return new Card(prefab.type, new TextureRegion(generateTexture(prefab)), prefab.actions, prefab.requirement, prefab.duration, prefab.rarity);
    }

    private static Pixmap copyPixmap(Pixmap base) {
        Pixmap copy = new Pixmap(base.getWidth(), base.getHeight(), base.getFormat());
        copy.drawPixmap(base, 0, 0);
        return copy;
    }

    private Texture generateTexture(CardPrefab prefab) {

        Pixmap background = copyPixmap(this.frontTemplate);
        Pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
        Pixmap image = new Pixmap(Gdx.files.internal("images/derExmatrikulator.png"));
        background.drawPixmap(image, 25, 49);


        String[] lines = descriptionToLines(prefab.description, 200);
        BitmapFont.TextBounds[] bounds = new BitmapFont.TextBounds[lines.length];

        for (int i = 0; i < lines.length; i++) {
            bounds[i] = font.getBounds(lines[i]);
        }

        float width = 0;
        float height = 0;
        for (BitmapFont.TextBounds bound : bounds) {
            width = Math.max(width, bound.width);
            height += Math.abs(bound.height);
        }

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int)Math.ceil(width), (int)Math.ceil(height), false);
        SpriteBatch batch = new SpriteBatch();

        //frameBuffer.begin();
        batch.begin();
        batch.enableBlending();
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float yOffset = 0;
        batch.setColor(Color.WHITE);
        for (int i = 0; i < lines.length; i++) {
            font.draw(batch, lines[i], 0, yOffset);
            yOffset += Math.abs(bounds[i].height);
        }

        batch.end();
        frameBuffer.end();

        Sprite sprite = new Sprite(frameBuffer.getColorBufferTexture());
        TextureData data = sprite.getTexture().getTextureData();
        background.drawPixmap(data.consumePixmap(), 0, 40);
        batch.dispose();
        frameBuffer.dispose();

        return new Texture(background);
    }

    private String[] descriptionToLines(String desc, float maxWidth) {
        String[] words = desc.trim().replaceAll("[ ]+", " ").split(" ");
        List<String> lines = new ArrayList<>();

        final float spaceWidth = font.getSpaceWidth();
        maxWidth += spaceWidth;

        for (int i = 0; i < words.length;) {
            float lineWidth = 0;
            StringBuilder line = new StringBuilder();
            while (i < words.length) {
                float width = font.getBounds(words[i]).width + spaceWidth;
                if (width > maxWidth) {
                    throw new IllegalArgumentException("Given description has a word that is longer than the max width!");
                }
                if (lineWidth + width > maxWidth) {
                    break;
                }
                line.append(' ').append(words[i]);
                lineWidth += width;
                i++;
            }

            lines.add(line.toString().substring(1));
        }

        return lines.toArray(new String[lines.size()]);
    }
}
