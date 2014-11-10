package de.cubeisland.games.dhbw.resource;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.resource.bag.*;
import life.banana4.util.resourcebags.Resources;

/**
 * This class is just a container of the resource bags used by the game
 * @author Phillip Schichtel
 * @author Jonas Dann
 */
public class DHBWResources extends Resources {
    /**
     * The entity prefabs used to create new entities.
     */
    public Entities entities;

    /**
     * The fonts used by the game.
     */
    public Fonts fonts;

    /**
     * The textures that will be used in game.
     */
    public Images images;

    /**
     * The card prefabs that define the cards.
     */
    public Cards cards;

    /**
     * The songs that are playing in game.
     */
    public Songs songs;

    /**
     * The sounds that will be played in game.
     */
    public Sounds sounds;

    /**
     * Constructs new instances of the resource bags
     *
     * @param reflector a Reflector instance to be used to load YAML files
     */
    public DHBWResources(Reflector reflector) {
        entities = new Entities(reflector);
        fonts = new Fonts();
        images = new Images();
        cards = new Cards(reflector, fonts);
        songs = new Songs();
        sounds = new Sounds();
    }
}
