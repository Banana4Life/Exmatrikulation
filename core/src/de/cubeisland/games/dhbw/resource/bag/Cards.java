package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.component.Card;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.entity.object.TextObject;
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
    private final Fonts fonts;
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
    public Card eventdiplbusinessinf;
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
    public Card eventprogramming1;
    public Card eventprogramming2;
    public Card eventsaturday;
    public Card eventworkflowlecture;
    // item cards
    public Card itembusinessadbook;
    public Card itemcbook;
    public Card itemincapacitycertificate;
    public Card itemlaptop;
    public Card itemmoreram;
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
    private Reflector reflector;
    private TextureRegion frontTemplate;
    private TextureRegion backTexture;
    private BitmapFont font;

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
        final String id = field.getName();
        System.out.println(basedir.child(id + ".yml").getPath());
        CardPrefab prefab = this.reflector.load(CardPrefab.class, basedir.child(id + ".yml").getInputStream());
        TextureRegion image;
        try {
            image = new TextureRegion(new Texture(fieldToFileRef(field, basedir).getPath() + ".png"));
        } catch (GdxRuntimeException e) {
            image = new TextureRegion(new Texture(basedir.child("eventexmatrikulator.png").getPath()));
            Gdx.app.log("error", "card image " + field.getName() + ".png not found!");
        }


        Card c = new Card(id, prefab.type, new CardObject(generateTexture(prefab, image), this.backTexture), prefab.actions, prefab.requirement, prefab.duration, prefab.rarity);

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

        font.setColor(Color.LIGHT_GRAY);
        font.draw(b, prefab.name, CONTENT_PADDING + TEXT_PADDING, TITLE_OFFSET);

        image.flip(false, true);
        b.draw(image, CONTENT_PADDING, IMAGE_OFFSET);

        List<String> lines = new ArrayList<>();
        for (String line : prefab.description) {
            lines.addAll(descriptionToLines(line, this.frontTemplate.getRegionWidth() - 2 * (CONTENT_PADDING + TEXT_PADDING)));
        }

        float yOffset = DESCR_OFFSET + TEXT_PADDING;
        font.setColor(Color.WHITE);
        for (String line : lines) {
            yOffset = TextObject.renderMultiline(line, b, font, (float) CONTENT_PADDING + TEXT_PADDING, yOffset, TEXT_PADDING, 1);
        }

        final CardPrefab.Requirement r = prefab.requirement;
        if (r.subject != null) {
            final String text = r.value + " " + r.subject;
            final float height = font.getBounds(text).height;
            font.setColor(Color.ORANGE);
            font.draw(b, text, CONTENT_PADDING + TEXT_PADDING, frontTemplate.getRegionHeight() - CONTENT_PADDING - TEXT_PADDING - height);
        }

        if (prefab.duration != 0) {
            String text = prefab.duration + "";
            TextBounds bounds = font.getBounds(text);
            float x = frontTemplate.getRegionWidth() - CONTENT_PADDING - TEXT_PADDING - bounds.width;
            float y = frontTemplate.getRegionHeight() - CONTENT_PADDING - TEXT_PADDING - bounds.height;
            font.setColor(Color.GREEN);
            font.draw(b, text, x, y);
        }

        b.end();
        fo.end();

        TextureRegion texture = new TextureRegion(fo.getColorBufferTexture(), 0, 0, frontTemplate.getRegionWidth(), frontTemplate.getRegionHeight());
        b.dispose();

        return texture;
    }

    private List<String> descriptionToLines(String desc, float maxWidth) {
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

        return lines;
    }
}
