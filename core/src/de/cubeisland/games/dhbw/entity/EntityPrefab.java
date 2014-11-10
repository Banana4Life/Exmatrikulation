package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.games.dhbw.util.Prefab;

import java.util.ArrayList;
import java.util.List;

/**
 * This class specifies the entity configuration
 *
 * @author Jonas Dann
 */
public class EntityPrefab extends Prefab<Entity> {
    public List<Class<Component>> components = new ArrayList<>();

    public boolean matches(Entity entity) {
        return getFamily().matches(entity);
    }

    @SuppressWarnings("unchecked")
    private Family getFamily() {
        Family.Builder builder = Family.all();
        for (Class<Component> component : this.components) {
            builder.all(component);
        }
        return builder.get();
    }
}

