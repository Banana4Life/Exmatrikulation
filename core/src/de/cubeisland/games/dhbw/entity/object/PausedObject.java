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
 * This class is responsible for rendering the paused button
 * @author Tim Adamek
 */
public class PausedObject  implements RenderObject {
    public static final float SCALE = 0.1f;

    private ArrayList<Decal> decals = new ArrayList<>();
    private int count = 1;

    /**
     * The constructor creates 20 decals with the 20 faces of the dice.
     */
    public PausedObject() {
        Texture image = new Texture("images/pause.png"); //TODO
        for (int n = 0; n < 20; n++) {
            decals.add(Decal.newDecal(new TextureRegion(image, 0, n * 50, 50, 50), true));
        }
    }

    @Override
    public boolean isWithin(Camera camera, float screenX, float screenY) {
        final PerspectiveCamera pc = camera.getPerspective();
        final Vector3 topLeft = pc.project(new Vector3(decals.get(0).getVertices()[Decal.X1], decals.get(0).getVertices()[Decal.Y1], decals.get(0).getVertices()[Decal.Z1]));
        final Vector3 topRight = pc.project(new Vector3(decals.get(0).getVertices()[Decal.X2], decals.get(0).getVertices()[Decal.Y2], decals.get(0).getVertices()[Decal.Z2]));
        final Vector3 bottomLeft = pc.project(new Vector3(decals.get(0).getVertices()[Decal.X3], decals.get(0).getVertices()[Decal.Y3], decals.get(0).getVertices()[Decal.Z3]));
        final Vector3 bottomRight = pc.project(new Vector3(decals.get(0).getVertices()[Decal.X4], decals.get(0).getVertices()[Decal.Y4], decals.get(0).getVertices()[Decal.Z4]));

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

        for (Decal decal : decals) {
            decal.setScale(SCALE);
            game.getDecalBatch().add(decal);
        }
        game.getDecalBatch().flush();
    }

    /**
     * Updates the positions of the decals to one position and gets the current face to the top.
     *
     * @param transform The position to use.
     * @return Returns this.
     */
    public PausedObject update(Transform transform) {
        for (Decal decal : decals) {
            decal.setPosition(transform.getPosition());
            decal.setRotation(transform.getRotation());
        }
        decals.get(count - 1).getPosition().add(0, 0, 1);

        return this;
    }

}
