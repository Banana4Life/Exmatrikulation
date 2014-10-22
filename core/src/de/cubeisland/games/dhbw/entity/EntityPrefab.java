package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.engine.reflect.ReflectedYaml;
import de.cubeisland.games.dhbw.util.modelobject.ModelObject;
import de.cubeisland.games.dhbw.util.renderobject.RenderObject;

import java.util.ArrayList;

public class EntityPrefab extends ReflectedYaml {
    public ArrayList<Class<Component>>  components;
    public Class<RenderObject>          renderobject;
    public Class<ModelObject>           modelobject;

    public boolean matches(Entity entity) {
        return getFamily().matches(entity);
    }

    @SuppressWarnings("unchecked")
    private Family getFamily() {
        return Family.getFor((Class<Component>[]) components.toArray(new Class[components.size()]));
    }
}
