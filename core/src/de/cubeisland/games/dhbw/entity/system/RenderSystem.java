package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.RenderObject2D;
import de.cubeisland.games.dhbw.entity.RenderObject;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Transform;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The RenderSystem renders entities to the screen
 * It uses the Family {Transform, Render}
 */
public class RenderSystem extends IteratingSystem {
    private static final RenderOrder BY_RENDER_ORDER = new RenderOrder();

    private final Camera camera;
    private final DHBWGame game;

    private final ComponentMapper<Transform> transforms;
    private final ComponentMapper<Render> renders;

    private final PriorityQueue<QueuedObject> queue = new PriorityQueue<>(10, BY_RENDER_ORDER);

    /**
     * The constructor sets the camera and game and gets the ComponentMapper for Transform and Render
     *
     * @param camera The camera to use.
     * @param game   The DHBWGame.
     */
    public RenderSystem(Camera camera, DHBWGame game) {
        super(Family.all(Transform.class, Render.class).get());
        this.camera = camera;

        this.game = game;

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.renders = ComponentMapper.getFor(Render.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        List<QueuedObject> sprites = new ArrayList<>(this.queue.size());
        for (QueuedObject o : this.queue) {
            if (o.is2D()) {
                sprites.add(o);
            } else {
                o.render(this.game, this.camera);
            }
        }
        this.queue.clear();

        this.game.getDecalBatch().flush();

        for (QueuedObject o : sprites) {
            o.render(game, this.camera);
        }
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Transform transform = transforms.get(entity);
        RenderObject object = renders.get(entity).getObject();
        if (object != null) {
            this.queue.add(new QueuedObject(entity, object, transform));
        }
    }

    /**
     * The QueuedObject encapsulates an entity, an object and a transform and allows to render the object.
     *
     * @author Phillip Schichtel
     */
    private static final class QueuedObject {
        private final Entity entity;
        private final RenderObject object;
        private final Transform transform;

        /**
         * The constructor sets the member variables.
         *
         * @param e      The Entity to use.
         * @param object The RenderObject to use.
         * @param t      the Transform to use.
         */
        public QueuedObject(Entity e, RenderObject object, Transform t) {
            this.entity = e;
            this.object = object;
            this.transform = t;
        }

        /**
         * Whether this is an 2D object
         *
         * @return true if 2D
         */
        public boolean is2D() {
            return this.object instanceof RenderObject2D;
        }

        /**
         * Renders the RenderObject with the entity, transform, game and cam.
         *
         * @param game The DHBWGame to use.
         * @param cam  The Camera to use.
         */
        public void render(DHBWGame game, Camera cam) {
            object.render(game, cam, entity, transform);
        }
    }

    /**
     * RenderOrder allows to compare two QueuedObjects and order them.
     *
     * @author Phillip Schichtel
     */
    private static final class RenderOrder implements Comparator<QueuedObject> {
        @Override
        public int compare(QueuedObject a, QueuedObject b) {
            final float az = a.transform.getPosition().z;
            final float bz = b.transform.getPosition().z;

            if (az > bz) {
                return 1;
            }
            if (az < bz) {
                return -1;
            }
            return 0;
        }
    }
}
