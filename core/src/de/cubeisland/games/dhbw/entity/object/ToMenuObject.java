package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.RenderObject;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.component.Transform;

import java.util.ArrayList;

/**
 * @author Tim Adamek
 * @author Jonas Dann
 */
public class ToMenuObject implements RenderObject {
    public static final float SCALE = 0.1f;
    private Decal front;
    private Decal back;
    private boolean hover = false;

    /**
     * The constructor creates 20 decals with the 20 faces of the dice.
     */
    public ToMenuObject() {
        front = Decal.newDecal(new TextureRegion(new Texture("images/pause.png")), true);
        back = Decal.newDecal(new TextureRegion(new Texture("images/dice.png"), 50, 50), true);
    }

    @Override
    public boolean isWithin(Camera camera, float screenX, float screenY) {
        final PerspectiveCamera pc = camera.getPerspective();
        final Vector3 topLeft = pc.project(new Vector3(front.getVertices()[Decal.X1], front.getVertices()[Decal.Y1], front.getVertices()[Decal.Z1]));
        final Vector3 topRight = pc.project(new Vector3(front.getVertices()[Decal.X2], front.getVertices()[Decal.Y2], front.getVertices()[Decal.Z2]));
        final Vector3 bottomLeft = pc.project(new Vector3(front.getVertices()[Decal.X3], front.getVertices()[Decal.Y3], front.getVertices()[Decal.Z3]));
        final Vector3 bottomRight = pc.project(new Vector3(front.getVertices()[Decal.X4], front.getVertices()[Decal.Y4], front.getVertices()[Decal.Z4]));

        Array<Vector2> polygon = new Array<>();
        polygon.add(new Vector2(topLeft.x, topLeft.y));
        polygon.add(new Vector2(topRight.x, topRight.y));
        polygon.add(new Vector2(bottomRight.x, bottomRight.y));
        polygon.add(new Vector2(bottomLeft.x, bottomLeft.y));

        return Intersector.isPointInPolygon(polygon, new Vector2(screenX, screenY));
    }

    @Override
    public void render(DHBWGame game, Camera cam, Entity e, Transform transform) {
        update(transform);
        front.setScale(SCALE);
        back.setScale(SCALE);

        game.getDecalBatch().add(front);
        game.getDecalBatch().add(back);
        game.getDecalBatch().flush();
    }

    /**
     * Updates the positions of the decals to one position and gets the current face to the top.
     *
     * @param transform The position to use.
     * @return Returns this.
     */
    public ToMenuObject update(Transform transform) {
        front.setPosition(transform.getPosition().add(0, 0, hover ? 0.5f : 1));
        front.setRotation(transform.getRotation());
        back.setPosition(transform.getPosition());
        back.setRotation(transform.getRotation());

        hover = false;

        return this;
    }

    public ToMenuObject setHover(boolean hover) {
        this.hover = hover;
        return this;
    }
}
