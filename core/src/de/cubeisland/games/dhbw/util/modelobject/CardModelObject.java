package de.cubeisland.games.dhbw.util.modelobject;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class CardModelObject extends ModelObject {
    private Decal frontDecal;
    private Decal backDecal;

    private static TextureRegion backTex;

    @Override
    public boolean isClickOnModel(Camera camera, float screenX, float screenY) {
        Vector3 topLeft     = camera.project(new Vector3(frontDecal.getVertices()[Decal.X1], frontDecal.getVertices()[Decal.Y1], frontDecal.getVertices()[Decal.Z1]));
        Vector3 topRight    = camera.project(new Vector3(frontDecal.getVertices()[Decal.X2], frontDecal.getVertices()[Decal.Y2], frontDecal.getVertices()[Decal.Z2]));
        Vector3 bottomLeft  = camera.project(new Vector3(frontDecal.getVertices()[Decal.X3], frontDecal.getVertices()[Decal.Y3], frontDecal.getVertices()[Decal.Z3]));
        Vector3 bottomRight = camera.project(new Vector3(frontDecal.getVertices()[Decal.X4], frontDecal.getVertices()[Decal.Y4], frontDecal.getVertices()[Decal.Z4]));

        Array<Vector2> polygon = new Array<>();
        polygon.add(new Vector2(topLeft.x, topLeft.y));
        polygon.add(new Vector2(topRight.x, topRight.y));
        polygon.add(new Vector2(bottomRight.x, bottomRight.y));
        polygon.add(new Vector2(bottomLeft.x, bottomLeft.y));

        return Intersector.isPointInPolygon(polygon, new Vector2(screenX, screenY));
    }

    public CardModelObject setDecals(TextureRegion frontTexture) {
        this.frontDecal = Decal.newDecal(frontTexture.getRegionWidth(), frontTexture.getRegionHeight(), frontTexture, true);
        this.backDecal = Decal.newDecal(backTex.getRegionWidth(), backTex.getRegionHeight(), backTex, true);
        return this;
    }

    @Override
    public CardModelObject setPosition(Vector3 position) {
        Vector3 gap = frontDecal.getRotation().cpy().transform(new Vector3(0, 0, 1)).scl(0.1f);

        frontDecal.setPosition(position.cpy());
        backDecal.setPosition(position.cpy().sub(gap));

        return this;
    }

    @Override
    public CardModelObject setRotation(Quaternion rotation) {
        frontDecal.setRotation(rotation.cpy());
        backDecal.setRotation(rotation.cpy());

        return this;
    }

    @Override
    public Vector3 getPosition() {
        return frontDecal.getPosition();
    }

    public Decal getFrontDecal() {
        return frontDecal;
    }

    public Decal getBackDecal() {
        return backDecal;
    }

    public static void setBackTex(TextureRegion backTex) {
        CardModelObject.backTex = backTex;
    }
}
