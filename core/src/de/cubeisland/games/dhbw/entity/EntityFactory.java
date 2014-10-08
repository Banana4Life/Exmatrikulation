package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.lang.reflect.InvocationTargetException;

public class EntityFactory {
    public Entity getInstance(EntityPreFab preFab) {
        Entity entity = new Entity();
        for (Class<Component> component : preFab.components) {
            try {
                entity.add(component.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException();
            }
        }
        return entity;
    }
}
