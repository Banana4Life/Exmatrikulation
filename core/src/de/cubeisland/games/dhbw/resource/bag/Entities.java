package de.cubeisland.games.dhbw.resource.bag;

import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.EntityPreFab;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

public class Entities extends ResourceBag<EntityPreFab> {
    private Reflector reflector;

    public EntityPreFab card;

    public Entities(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    protected EntityPreFab load(FileRef basedir, Field field) {
        return this.reflector.load(EntityPreFab.class, basedir.child(field.getName()+".yml").getInputStream());
    }
}
