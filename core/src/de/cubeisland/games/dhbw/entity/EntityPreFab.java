package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.engine.reflect.ReflectedYaml;
import de.cubeisland.games.dhbw.util.renderobject.RenderObject;

import java.util.ArrayList;

public class EntityPreFab extends ReflectedYaml {
    public ArrayList<Class<Component>>  components;
    public RenderObject                 renderObject;

    public boolean matches(Entity entity) {
        return getFamily().matches(entity);
    }

    @SuppressWarnings("unchecked")
    private Family getFamily() {
        return Family.getFor((Class<Component>[]) components.toArray(new Class[components.size()]));
    }
}
