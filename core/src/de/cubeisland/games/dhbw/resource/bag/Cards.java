package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.component.Card;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

/**
 * This class holds all the card prefabs.
 *
 * @author Andreas Geis
 */
public class Cards extends ResourceBag<Card> {
    private Reflector reflector;
    private final Fonts fonts;
    private TextureRegion frontTemplate;
    private TextureRegion backTexture;
    private BitmapFont font;

    public Card dummy;

    //menu cards
    public Card menustorymode;
    public Card menufreemode;

    public Card menubusinessinf;
    public Card menubusinessad;
    public Card menuappliedinf;

    // event cards
    public Card eventalgebralecture;
    public Card eventanalysislecture;
    public Card eventclecture;
    public Card eventcommunicationlecture;
    public Card eventdiplwirtschinf;
    public Card eventexmatrikulator;
    public Card eventfirstmathlecture;
    public Card eventfortuneminister;
    public Card eventintroductionlecture;
    public Card eventjavalecture;
    public Card eventlunch;
    public Card eventmarketinglecture;
    public Card eventmathexam;
    public Card eventoospaceships;
    public Card eventoverslept;
    public Card eventparty;
    public Card eventprogrammingproject;
    public Card eventsaturday;
    public Card eventworkflowlecture;

    // item cards
    public Card itembusinessadbook;
    public Card itemcbook;
    public Card itemincapacitycertificate;
    public Card itemlaptop;
    public Card itemlaptopupgrade;
    public Card itemmathbook;
    public Card itemswag4nerds;
    public Card itemswagcap;
    public Card itemwaterbottle1;
    public Card itemwaterbottle2;

    public Card skillc;
    public Card skillinvestment;
    public Card skilldirectproof;
    public Card skillinduction;
    public Card skilljava;
    public Card skilllegobuilding;
    public Card skillprojectmanagement;
    public Card skillregex;
    public Card skillsmile;
    public Card skillwebserver;

    public Cards(Reflector reflector, Fonts fonts) {
        this.reflector = reflector;
        this.fonts = fonts;

        this.frontTemplate = new TextureRegion(new Texture("images/cardfront.png"));
        this.frontTemplate.flip(false, true);
        this.backTexture = new TextureRegion(new Texture("images/cardback.png"));
    }

    @Override
    public void build() {
        this.fonts.build();
        this.font = fonts.cardFont.flipped().getBitmapFont();
        super.build();
    }

    @Override
    protected Card load(FileRef basedir, Field field) {
        System.out.println(basedir.child(field.getName() + ".yml").getPath());
        CardPrefab prefab = this.reflector.load(CardPrefab.class, basedir.child(field.getName() + ".yml").getInputStream());
        TextureRegion image;
        try {
            image = new TextureRegion(new Texture(fieldToFileRef(field, basedir).getPath() + ".png"));
        } catch (GdxRuntimeException e) {
            image = new TextureRegion(new Texture(basedir.child("eventexmatrikulator.png").getPath()));
            Gdx.app.log("error", "card image " + field.getName() + ".png not found!");
        }


        Card c = new Card(prefab.type, new CardObject(generateTexture(prefab, image), this.backTexture), prefab.actions, prefab.requirement, prefab.duration, prefab.rarity);

        image.getTexture().dispose();

        return c;
    }

    private TextureRegion generateTexture(CardPrefab prefab, TextureRegion image) {

        final int CONTENT_PADDING = 25;
        final int SPACER = 5;
        final int TEXT_PADDING = 3;

        final int TITLE_OFFSET = CONTENT_PADDING;
        final int IMAGE_OFFSET = CONTENT_PADDING + TEXT_PADDING * 2 + 13 + SPACER;
        final int DESCR_OFFSET = IMAGE_OFFSET + image.getRegionHeight() + SPACER;

        FrameBuffer fo = new FrameBuffer(RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        SpriteBatch b = new SpriteBatch();

        fo.begin();
        b.begin();

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b.draw(this.frontTemplate, 0, 0);

        font.draw(b, prefab.name, CONTENT_PADDING + TEXT_PADDING, TITLE_OFFSET);

        image.flip(false, true);
        b.draw(image, CONTENT_PADDING, IMAGE_OFFSET);

        String[] lines = descriptionToLines(prefab.description, this.frontTemplate.getRegionWidth() - 2 * (CONTENT_PADDING + TEXT_PADDING));

        float yOffset = DESCR_OFFSET + TEXT_PADDING;
        font.setColor(Color.WHITE);
        for (String line : lines) {
            font.draw(b, line, CONTENT_PADDING + TEXT_PADDING, yOffset);
            yOffset += font.getBounds(line).height + TEXT_PADDING;
        }

        b.end();
        fo.end();

        TextureRegion texture = new TextureRegion(fo.getColorBufferTexture(), 0, 0, frontTemplate.getRegionWidth(), frontTemplate.getRegionHeight());
        b.dispose();

        return texture;
    }

    private String[] descriptionToLines(String desc, float maxWidth) {
        String[] words = desc.trim().replaceAll("[ ]+", " ").split(" ");
        List<String> lines = new ArrayList<>();

        final float spaceWidth = font.getSpaceWidth();
        maxWidth += spaceWidth;

        for (int i = 0; i < words.length; ) {
            float lineWidth = 0;
            StringBuilder line = new StringBuilder();
            while (i < words.length) {
                float width = font.getBounds(words[i]).width + spaceWidth;
                if (width > maxWidth) {
                    throw new IllegalArgumentException("Given description has a word that is longer than the max width! " + words[i]);
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
