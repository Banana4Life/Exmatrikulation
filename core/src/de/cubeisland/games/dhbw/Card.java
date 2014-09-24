package de.cubeisland.games.dhbw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.utils.BetterQuaternion;

public class Card {
    private Board board;

    private Vector3             destPos;
    private BetterQuaternion    destRot;
    private Decal               frontDecal;
    private Decal               backDecal;
    private boolean             pickable;

    private static Card             cardPickedUp;
    private static TextureRegion    backTex;

    public Card(Board board, TextureRegion frontTex, Vector3 position) {
        this.board = board;

        this.destPos = position.cpy();

        this.frontDecal = Decal.newDecal(frontTex.getRegionWidth(), frontTex.getRegionHeight(), frontTex, true);
        this.frontDecal.setPosition(position);

        this.backDecal = Decal.newDecal(backTex.getRegionWidth(), backTex.getRegionHeight(), backTex, true);
        this.backDecal.setPosition(position);
    }

    public void render(float delta) {

        Vector3 unprojectedMouse = board.getGame().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), board.getGame().getCamera().project(frontDecal.getPosition().cpy()).z));

        if (Gdx.input.justTouched()) {
            if (cardPickedUp == null) {
                //TODO: Besser umsetzen. (bessere "Hitbox")
                if ((unprojectedMouse.x > frontDecal.getX() - frontDecal.getWidth() / 2f && unprojectedMouse.x < frontDecal.getX() + frontDecal.getWidth() / 2f) &&
                    (unprojectedMouse.y > frontDecal.getY() - frontDecal.getHeight() / 2f && unprojectedMouse.y < frontDecal.getY() + frontDecal.getHeight() / 2f)) {
                    cardPickedUp = this;
                    destPos = unprojectedMouse.cpy();
                }
            }
        } else if (Gdx.input.isTouched()) {
            if (cardPickedUp == this) {
                destPos = unprojectedMouse.cpy();
            }
        } else if (!Gdx.input.isTouched()) {
            cardPickedUp = null;
        }

        Vector2 moveVec = new Vector2(0, 0);

        if (destPos == null) {
            destPos = frontDecal.getPosition().cpy();
        }

        Vector3 dummy = destPos.cpy().sub(frontDecal.getPosition());
        moveVec.set(dummy.x, dummy.y).scl(0.5f);

        if (cardPickedUp == this) {
            frontDecal.setZ(-90);
        } else {
            frontDecal.setZ(-100);
        }

        if (destRot == null) {
            destRot = new BetterQuaternion(new Vector3(1, 0, 0), 0);
        }

        frontDecal.setRotation((float) Math.cos(Math.toRadians(moveVec.angle())) * moveVec.len() * 10f + destRot.getAngleAround(1, 0, 0), -(float) Math.sin(Math.toRadians(moveVec.angle())) * moveVec.len() * 10f + destRot.getAngleAround(0, 1, 0), destRot.getAngleAround(0, 0, 1));
        backDecal.setRotation(frontDecal.getRotation().cpy());

        Vector3 gap = frontDecal.getRotation().transform(new Vector3(0, 0, 1)).scl(0.1f);

        frontDecal.getPosition().add(moveVec.x, moveVec.y, frontDecal.getZ());
        backDecal.setPosition(frontDecal.getX() - gap.x, frontDecal.getY() - gap.y, frontDecal.getZ() - gap.z);

        board.getGame().getDecalBatch().add(frontDecal);
        board.getGame().getDecalBatch().add(backDecal);
    }

    public boolean isPointOnProjectedCard(DHBWGame game, Vector2 point) {
        Vector3 topLeft     = game.getCamera().project(new Vector3(frontDecal.getVertices()[Decal.X1], frontDecal.getVertices()[Decal.Y1], frontDecal.getVertices()[Decal.Z1]));
        Vector3 bottomRight = game.getCamera().project(new Vector3(frontDecal.getVertices()[Decal.X4], frontDecal.getVertices()[Decal.Y4], frontDecal.getVertices()[Decal.Z4]));

        return (point.x > topLeft.x && point.x < bottomRight.x) && (point.y > topLeft.y && point.y < bottomRight.y);
    }

    public Card setDestPos(Vector3 destPos) {
        this.destPos = destPos;
        return this;
    }

    public Card setDestRot(BetterQuaternion destRot) {
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
