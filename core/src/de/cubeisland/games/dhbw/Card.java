package de.cubeisland.games.dhbw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.utils.BetterQuaternion;

public class Card {
    private Vector3             destPos;
    private BetterQuaternion    destRot;
    private Decal               frontDecal;
    private Decal               backDecal;

    private static Card             cardPickedUp;
    private static TextureRegion    backTex;

    public Card(TextureRegion frontTex, Vector3 position) {
        this.destPos = position.cpy();

        this.frontDecal = Decal.newDecal(frontTex.getRegionWidth(), frontTex.getRegionHeight(), frontTex, true);
        this.frontDecal.setPosition(position);

        this.backDecal = Decal.newDecal(backTex.getRegionWidth(), backTex.getRegionHeight(), backTex, true);
        this.backDecal.setPosition(position);
    }

    public void render(DHBWGame game, float delta) {

        Vector3 unprojectedMouse = game.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), game.getCamera().project(frontDecal.getPosition().cpy()).z));

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

        game.getDecalBatch().add(frontDecal);
        game.getDecalBatch().add(backDecal);
    }

    public Card setDestPos(Vector3 destPos) {
        this.destPos = destPos;
        return this;
    }

    public Card setDestRot(BetterQuaternion destRot) {
        this.destRot = destRot;
        return this;
    }

    public static void setBackTex(TextureRegion backTex) {
        Card.backTex = backTex;
    }
}
