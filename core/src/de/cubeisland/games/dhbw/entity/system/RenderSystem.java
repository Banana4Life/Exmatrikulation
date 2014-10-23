package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Model;
import de.cubeisland.games.dhbw.entity.component.Renderable;
import de.cubeisland.games.dhbw.entity.component.Transform;

import java.util.Comparator;
import java.util.PriorityQueue;

public class RenderSystem extends IteratingSystem {
    private static final RenderOrder BY_RENDER_ORDER = new RenderOrder();

    private final PerspectiveCamera camera;
    private final DHBWGame game;

    private final ComponentMapper<Model> models;
    private final ComponentMapper<Renderable> renderables;

    private final PriorityQueue<RenderObject> queue = new PriorityQueue<>(10, BY_RENDER_ORDER);

    public RenderSystem(PerspectiveCamera camera, DHBWGame game) {
        super(Family.getFor(Model.class, Renderable.class));
        this.camera = camera;

        this.game = game;

        this.models = ComponentMapper.getFor(Model.class);
        this.renderables = ComponentMapper.getFor(Renderable.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Entity e;
        for (RenderObject o : this.queue) {
            e = o.entity;
            renderables.get(e).render(this.camera, e, this.game);
        }

        this.queue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Model model = models.get(entity);
        this.queue.add(new RenderObject(entity, model.getModelObject().getPosition()));
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
