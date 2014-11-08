package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Text;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.object.ImageObject;
import de.cubeisland.games.dhbw.entity.object.TextObject;
import de.cubeisland.games.dhbw.resource.bag.Entities;
import de.cubeisland.games.dhbw.resource.font.Font;
import de.cubeisland.games.dhbw.util.Factory;

/**
 * This factory creates entities from their prefabs
 *
 * @author Jonas Dann
 * @author Phillip Schichtel
 */
public class EntityFactory implements Factory<Entity, EntityPrefab> {

    private final Entities prefabs;

    public EntityFactory(Entities prefabs) {
        this.prefabs = prefabs;
    }

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

    public Entity createImage(String internalPath, Vector3 pos, float scale) {
        Entity e = create(prefabs.image);
        e.getComponent(Render.class).setObject(new ImageObject(new Texture(internalPath)));
        e.getComponent(Transform.class).setPosition(pos).setScale(scale);
        return e;
    }

    public Entity createText(String text, Font font, Color color, Vector2 pos) {
        Entity e = create(prefabs.text);
        e.getComponent(Text.class).set(text);
        e.getComponent(Render.class).setObject(new TextObject(font, color));
        e.getComponent(Transform.class).setPosition(new Vector3(pos.x, pos.y, 0));
        return e;
    }
}
