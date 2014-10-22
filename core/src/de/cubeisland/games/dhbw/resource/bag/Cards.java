package de.cubeisland.games.dhbw.resource.bag;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

public class Cards extends ResourceBag<CardPrefab> {
    private Reflector reflector;

    // initialization of all cards; name must be equal to name of the config-file
    // event cards
    public CardPrefab eventCard1;

    // object cards
    public CardPrefab objectCard1;

    // ability cards
    public CardPrefab abilityCard1;

    public Cards(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    protected CardPrefab load(FileRef basedir, Field field) {
        return this.reflector.load(CardPrefab.class, basedir.child(field.getName()+".yml").getInputStream());
    }
}
