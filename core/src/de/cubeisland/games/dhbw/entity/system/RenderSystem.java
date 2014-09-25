package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Renderable;
import de.cubeisland.games.dhbw.entity.component.Transform;

import java.util.Comparator;
import java.util.PriorityQueue;

public class RenderSystem extends IteratingSystem {

    private static final RenderOrder BY_RENDER_ORDER = new RenderOrder();

    private final ComponentMapper<Transform> transforms;
    private final ComponentMapper<Renderable> renderables;

    private final PriorityQueue<RenderObject> queue = new PriorityQueue<>(10, BY_RENDER_ORDER);

    public RenderSystem(Family family) {
        super(Family.getFor(Transform.class, Renderable.class));

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.renderables = ComponentMapper.getFor(Renderable.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Entity e;
        Transform transform;
        Texture texture;
        for (RenderObject o : this.queue) {
            e = o.entity;
            transform = transforms.get(e);
            texture = renderables.get(e).getTexture();

            // TODO render here
        }

        this.queue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Transform transform = transforms.get(entity);
        this.queue.add(new RenderObject(entity, transform.getPosition()));
    }

    private static final class RenderObject {
        public final Entity entity;
        public long order;

        public RenderObject(Entity e, Vector3 pos) {
            this.entity = e;
            this.order &= Float.floatToRawIntBits(pos.z);
            this.order <<= 32;
        }
    }

    private static final class RenderOrder implements Comparator<RenderObject> {
        @Override
        public int compare(RenderObject a, RenderObject b) {
            if (a.order > b.order) {
                return 1;
            }
            if (a.order < b.order) {
                return -1;
            }
            return 0;
        }
    }
}
