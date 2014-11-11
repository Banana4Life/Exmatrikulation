package de.cubeisland.games.dhbw.resource.bag;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.EntityPrefab;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

/**
 * Holds all the entity prefabs.
 *
 * @author Phillip Schichtel
 */
public class Entities extends ResourceBag<EntityPrefab> {
    private Reflector reflector;

    public EntityPrefab card;
    public EntityPrefab deck;
    public EntityPrefab dice;
    public EntityPrefab cardhand;
    public EntityPrefab tomenu;
    public EntityPrefab camera;
    public EntityPrefab image;
    public EntityPrefab text;
    public EntityPrefab status;

    public Entities(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    protected EntityPrefab load(FileRef basedir, Field field) {
        return this.reflector.load(EntityPrefab.class, basedir.child(field.getName() + ".yml").getInputStream());
    }
}
