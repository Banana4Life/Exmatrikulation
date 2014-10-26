package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.entity.component.Renderable;
import de.cubeisland.games.dhbw.util.Factory;
import de.cubeisland.games.dhbw.util.renderobject.RenderObject;

public class EntityFactory implements Factory<Entity, EntityPrefab> {

    public Entity create(EntityPrefab preFab) {
        Entity entity = new Entity();
        for (Class<Component> component : preFab.components) {
            try {
                if (Renderable.class.isAssignableFrom(component)) {
                    entity.add(component.getConstructor(RenderObject.class).newInstance(preFab.renderobject.getConstructor().newInstance()));
                } else {
                    entity.add(component.getConstructor().newInstance());
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to create an instance of the component '" + component.getName() + "'!", e);
            }
        }
        return entity;
    }
}
