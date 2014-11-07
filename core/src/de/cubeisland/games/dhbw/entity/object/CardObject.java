package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.RenderObject;

/**
 * CardObject is the RenderObject that encapsulates a front and a back Decal which are the faces of the card.
 * The back texture is static for all cards.
 * @author  Jonas Dann
 */
public class CardObject implements RenderObject {
    public static final float SCALE = 0.1f;
    public static final float GAP   = .6f;
    private final Decal front;
    private final Decal back;

    private static TextureRegion backTex;

    /**
     * Sets the front and back Decal.
     * @param frontTexture The front texture to use.
     */
    public CardObject(TextureRegion frontTexture) {
        this.front = Decal.newDecal(frontTexture.getRegionWidth(), frontTexture.getRegionHeight(), frontTexture, true);
        this.back = Decal.newDecal(backTex.getRegionWidth(), backTex.getRegionHeight(), backTex, true);
    }

    @Override
    public boolean isWithin(Camera camera, float screenX, float screenY) {
        Vector3 topLeft     = camera.project(new Vector3(front.getVertices()[Decal.X1], front.getVertices()[Decal.Y1], front.getVertices()[Decal.Z1]));
        Vector3 topRight    = camera.project(new Vector3(front.getVertices()[Decal.X2], front.getVertices()[Decal.Y2], front.getVertices()[Decal.Z2]));
        Vector3 bottomLeft  = camera.project(new Vector3(front.getVertices()[Decal.X3], front.getVertices()[Decal.Y3], front.getVertices()[Decal.Z3]));
        Vector3 bottomRight = camera.project(new Vector3(front.getVertices()[Decal.X4], front.getVertices()[Decal.Y4], front.getVertices()[Decal.Z4]));

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
        batch.flush();
    }

    /**
     * Helper to update the positions and rotation of the Decals to the given transform.
     * @param transform The transform to set the Decal position and rotation to
     */
    private void update(Transform transform) {
        setPosition(this.front, this.back, transform.getPosition());
        setRotation(this.front, this.back, transform.getRotation());
    }

    /**
     * Helper to set the position of the front and back Decal to a given position with the back shifted by a small gap.
     * @param front Front Decal to work with.
     * @param back Back Decal to work with.
     * @param position Position to use.
     */
    private static void setPosition(Decal front, Decal back, Vector3 position) {
        Vector3 gap = front.getRotation().cpy().transform(new Vector3(0, 0, 1)).scl(GAP);

        front.setPosition(position.cpy());
        back.setPosition(position.cpy().sub(gap));
    }

    /**
     * Helper to set the rotation of the front and back Decal to a given rotation.
     * @param front Front Decal to work with.
     * @param back Back Decal to work with.
     * @param rotation Rotation to use.
     */
    public static void setRotation(Decal front, Decal back, Quaternion rotation) {
        front.setRotation(rotation.cpy());
        back.setRotation(rotation.cpy());
    }

    /**
     * Returns the front Decal.
     * @return Front Decal
     */
    public Decal getFront() {
        return front;
    }

    /**
     * Returns the back Decal.
     * @return Back Decal
     */
    public Decal getBack() {
        return back;
    }

    /**
     * Sets the static back texture.
     * @param backTex back texture to use.
     */
    public static void setBackTex(TextureRegion backTex) {
        CardObject.backTex = backTex;
    }
}
