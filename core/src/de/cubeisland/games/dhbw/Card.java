package de.cubeisland.games.dhbw;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Card {
    private Board board;

    private Vector3             destPos;
    private Quaternion          destRot;
    private Decal               frontDecal;
    private Decal               backDecal;
    private boolean             pickable;

    private static TextureRegion backTex;

    public Card(Board board, TextureRegion frontTex, Vector3 position) {
        this.board = board;

        this.destPos = position.cpy();

        this.frontDecal = Decal.newDecal(frontTex.getRegionWidth(), frontTex.getRegionHeight(), frontTex, true);
        this.frontDecal.setPosition(position);

        this.backDecal = Decal.newDecal(backTex.getRegionWidth(), backTex.getRegionHeight(), backTex, true);
        this.backDecal.setPosition(position);
    }

    public void render(float delta) {
        if (destPos == null) {
            destPos = frontDecal.getPosition().cpy();
        }

        Vector3 moveVec = destPos.cpy().sub(frontDecal.getPosition()).scl(0.5f);
        Vector2 moveVec2D = new Vector2(moveVec.x, moveVec.y);

        if (destRot == null) {
            destRot = new Quaternion(new Vector3(1, 0, 0), 0);
        }

        frontDecal.setRotation((float) Math.cos(Math.toRadians(moveVec2D.angle())) * moveVec2D.len() * 5f + destRot.getAngleAround(1, 0, 0), -(float) Math.sin(Math.toRadians(moveVec2D.angle())) * moveVec2D.len() * 5f + destRot.getAngleAround(0, 1, 0), destRot.getAngleAround(0, 0, 1));
        backDecal.setRotation(frontDecal.getRotation().cpy());

        Vector3 gap = frontDecal.getRotation().transform(new Vector3(0, 0, 1)).scl(0.1f);

        frontDecal.getPosition().add(moveVec.x, moveVec.y, moveVec.z);
        backDecal.setPosition(frontDecal.getX() - gap.x, frontDecal.getY() - gap.y, frontDecal.getZ() - gap.z);

        board.getGame().getDecalBatch().add(frontDecal);
        board.getGame().getDecalBatch().add(backDecal);
    }

    public boolean isClickOnProjectedCard(float screenX, float screenY) {
        Vector3 topLeft     = board.getGame().getCamera().project(new Vector3(frontDecal.getVertices()[Decal.X1], frontDecal.getVertices()[Decal.Y1], frontDecal.getVertices()[Decal.Z1]));
        Vector3 topRight    = board.getGame().getCamera().project(new Vector3(frontDecal.getVertices()[Decal.X2], frontDecal.getVertices()[Decal.Y2], frontDecal.getVertices()[Decal.Z2]));
        Vector3 bottomLeft  = board.getGame().getCamera().project(new Vector3(frontDecal.getVertices()[Decal.X3], frontDecal.getVertices()[Decal.Y3], frontDecal.getVertices()[Decal.Z3]));
        Vector3 bottomRight = board.getGame().getCamera().project(new Vector3(frontDecal.getVertices()[Decal.X4], frontDecal.getVertices()[Decal.Y4], frontDecal.getVertices()[Decal.Z4]));

        Array<Vector2> polygon = new Array<>();
        polygon.add(new Vector2(topLeft.x, topLeft.y));
        polygon.add(new Vector2(topRight.x, topRight.y));
        polygon.add(new Vector2(bottomRight.x, bottomRight.y));
        polygon.add(new Vector2(bottomLeft.x, bottomLeft.y));

        return Intersector.isPointInPolygon(polygon, new Vector2(screenX, screenY));
        //return ((screenX > topLeft.x && screenX < bottomRight.x) || (screenX < topLeft.x && screenX > bottomRight.x)) && ((screenY < topLeft.y && screenY > bottomRight.y) || (screenY > topLeft.y && screenY < bottomRight.y));
    }

    public Card setDestPos(Vector3 destPos) {
        this.destPos = destPos;
        return this;
    }

    public Vector3 getDestPos() {
        return destPos;
    }

    public Card setScreenDestPos(float screenX, float screenY, float z) {
        this.destPos = board.getGame().getCamera().unproject(new Vector3(screenX, screenY, board.getGame().getCamera().project(frontDecal.getPosition().cpy()).z)).set(destPos.x, destPos.y, z);
        return this;
    }

    public Card setDestRot(Quaternion destRot) {
        this.destRot = destRot;
        return this;
    }

    public boolean isPickable() {
        return pickable;
    }

    public Card setPickable(boolean pickable) {
        this.pickable = pickable;
        return this;
    }

    public static void setBackTex(TextureRegion backTex) {
        Card.backTex = backTex;
    }
}
