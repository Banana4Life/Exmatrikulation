package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.entity.component.Renderable;
import de.cubeisland.games.dhbw.util.renderobject.RenderObject;

import java.lang.reflect.InvocationTargetException;

public class EntityFactory {
    private Engine engine;

    public EntityFactory(Engine engine) {
        this.engine = engine;
    }

    public Entity getInstance(EntityPreFab preFab) {
        Entity entity = new Entity();
        engine.addEntity(entity);
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
