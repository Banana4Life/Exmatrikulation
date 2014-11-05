package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.util.Factory;

/**
 * This factory creates entities from their prefabs
 *
 * @author Jonas Dann
 * @author Phillip Schichtel
 */
public class EntityFactory implements Factory<Entity, EntityPrefab> {

    public Entity create(EntityPrefab preFab) {
        Entity entity = new Entity();
        for (Class<Component> component : preFab.components) {
            try {
                entity.add(component.getConstructor().newInstance());
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to create an instance of the component '" + component.getName() + "'!", e);
            }
        }
        return entity;
    }
}
