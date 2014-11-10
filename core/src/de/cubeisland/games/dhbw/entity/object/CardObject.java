package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.RenderObject;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * CardObject is the RenderObject that encapsulates a front and a back Decal which are the faces of the card.
 * The back texture is static for all cards.
 *
 * @author Jonas Dann
 */
public class CardObject implements RenderObject {
    public static final float SCALE = 0.1f;
    public static final float GAP = 1.0f;
    private final Decal front;
    private final Decal back;

    /**
     * Sets the front and back Decal.
     *
     * @param front The front texture to use.
     */
    public CardObject(TextureRegion front, TextureRegion back) {
        this.front = Decal.newDecal(front, true);
        this.back = Decal.newDecal(back, true);
    }

    /**
     * Object to copy a CardObject
     *
     * @param object The object to copy.
     */
    public CardObject(CardObject object) {
        this.front = Decal.newDecal(object.getFront().getTextureRegion(), true);
        this.back = Decal.newDecal(object.getBack().getTextureRegion(), true);
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

        DecalBatch batch = game.getDecalBatch();
        batch.add(this.front);
        batch.add(this.back);
        batch.flush(); // TODO this should be unnecessary and probably hurts performance, but a few cards go invisible without it
    }

    /**
     * Helper to update the positions and rotation of the Decals to the given transform.
     *
     * @param transform The transform to set the Decal position and rotation to
     */
    private void update(Transform transform) {
        setRotation(this.front, this.back, transform.getRotation());
        setPosition(this.front, this.back, transform.getPosition());
    }

    /**
     * Helper to set the position of the front and back Decal to a given position with the back shifted by a small gap.
     *
     * @param front    Front Decal to work with.
     * @param back     Back Decal to work with.
     * @param position Position to use.
     */
    private static void setPosition(Decal front, Decal back, Vector3 position) {
        Vector3 gap = front.getRotation().cpy().transform(new Vector3(0, 0, 1)).scl(GAP);

        front.setPosition(position.cpy());
        back.setPosition(position.cpy().sub(gap));
    }

    /**
     * Helper to set the rotation of the front and back Decal to a given rotation.
     *
     * @param front    Front Decal to work with.
     * @param back     Back Decal to work with.
     * @param rotation Rotation to use.
     */
    private static void setRotation(Decal front, Decal back, Quaternion rotation) {
        front.setRotation(rotation.cpy());
        back.setRotation(rotation.cpy());
    }

    public Decal getFront() {
        return front;
    }

    public Decal getBack() {
        return back;
    }
}
