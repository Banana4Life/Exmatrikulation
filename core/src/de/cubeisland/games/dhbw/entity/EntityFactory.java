package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.entity.component.Model;
import de.cubeisland.games.dhbw.entity.component.Renderable;
import de.cubeisland.games.dhbw.util.modelobject.ModelObject;
import de.cubeisland.games.dhbw.util.renderobject.RenderObject;

public class EntityFactory {

    public Entity create(EntityPrefab preFab) {
        Entity entity = new Entity();
        for (Class<Component> component : preFab.components) {
            try {
                if (Renderable.class.isAssignableFrom(component)) {
                    entity.add(component.getConstructor(RenderObject.class).newInstance(preFab.renderobject.getConstructor().newInstance()));
                } else if (Model.class.isAssignableFrom(component)) {
                    entity.add(component.getConstructor(ModelObject.class).newInstance(preFab.modelobject.getConstructor().newInstance()));
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
