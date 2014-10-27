package de.cubeisland.games.dhbw.resource;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.resource.bag.Cards;
import de.cubeisland.games.dhbw.resource.bag.Entities;
import life.banana4.util.resourcebags.Resources;

public class DHBWResources extends Resources {
    public Entities entities;
    public Cards    cards;

    public DHBWResources(Reflector reflector) {
        entities = new Entities(reflector);
        cards    = new Cards(reflector);
    }
}
