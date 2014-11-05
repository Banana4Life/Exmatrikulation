package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.component.Card;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

/**
 * Holds all the card prefabs.
 */
public class Cards extends ResourceBag<Card> {
    private Reflector reflector;

    public Card dummy;

    //menu cards
    public Card menustorymode;
    public Card menufreemode;
    public Card menumultimode;

    // initialization of all cards; name must be equal to name of the config-file
    // event cards
    public Card eventCard1;

    // item cards
    public Card itemCard1;
    public Card itemCard2;

    public Cards(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    protected Card load(FileRef basedir, Field field) {
        CardPrefab prefab = this.reflector.load(CardPrefab.class, basedir.child(field.getName()+".yml").getInputStream());

        //TODO Code to construct the texture
        TextureRegion texture = new TextureRegion(new Texture("cards/cardfront.png"));

        return new Card(prefab.type, texture, prefab.actions, prefab.requirement, prefab.duration, prefab.rarity);
    }
}
