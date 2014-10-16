package de.cubeisland.games.dhbw.resource.bag;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPreFab;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

public class Cards extends ResourceBag<CardPreFab> {
    private Reflector reflector;

    // Instanzierung aller Karten; Name muss dem Namen der entsprechenden config-Datei entsprechen
    // Ereigniskarten
    public CardPreFab eventCard1;

    // Objektkarten
    public CardPreFab objectCard1;

    // Fähigkeitskarten
    public CardPreFab abilityCard1;

    public Cards(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    protected CardPreFab load(FileRef basedir, Field field) {
        return this.reflector.load(CardPreFab.class, basedir.child(field.getName()+".yml").getInputStream());
    }
}
