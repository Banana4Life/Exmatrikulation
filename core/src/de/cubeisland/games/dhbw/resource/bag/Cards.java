package de.cubeisland.games.dhbw.resource.bag;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPreFab;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

public class Cards extends ResourceBag<CardPreFab> {
    private Reflector reflector;

    // initialization of all cards; name must be equal to name of the config-file
    // event cards
    public CardPreFab eventCard1;

    // object cards
    public CardPreFab objectCard1;

    // ability cards
    public CardPreFab abilityCard1;

    public Cards(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    protected CardPreFab load(FileRef basedir, Field field) {
        return this.reflector.load(CardPreFab.class, basedir.child(field.getName()+".yml").getInputStream());
    }
}
