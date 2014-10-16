package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Transform;

public class CardRenderObject extends RenderObject {
    private Decal frontDecal;
    private Decal backDecal;

    private static TextureRegion backTex;

    public boolean isClickOnProjectedCard(Camera camera, float screenX, float screenY) {
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

    @Override
    public void render(Transform transform, DHBWGame game) {
        frontDecal.setRotation(transform.getRotation().cpy());
        frontDecal.setPosition(transform.getPosition().cpy());
        backDecal.setRotation(transform.getRotation().cpy());
        backDecal.setPosition(transform.getPosition().cpy());
        backDecal.translate(frontDecal.getRotation().transform(new Vector3(0, 0, 1)).scl(0.1f));

        game.getDecalBatch().add(frontDecal);
        game.getDecalBatch().add(backDecal);
        game.getDecalBatch().flush();

        /*
        public void render(float delta) {
            if (destPos == null) {
                destPos = frontDecal.getPosition().cpy();
            }

            Vector3 moveVec = destPos.cpy().sub(frontDecal.getPosition()).scl(0.2f);
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
        */
    }

    public CardRenderObject setFrontDecal(Decal frontDecal) {
        this.frontDecal = frontDecal;
        return this;
    }
    public CardRenderObject setFrontDecal(TextureRegion texture) {
        this.frontDecal = Decal.newDecal(texture.getRegionWidth(), texture.getRegionHeight(), texture, true);
        return this;
    }

    public CardRenderObject setBackDecal(Decal backDecal) {
        this.backDecal = backDecal;
        return this;
    }
    public CardRenderObject setBackDecal() {
        this.frontDecal = Decal.newDecal(backTex.getRegionWidth(), backTex.getRegionHeight(), backTex, true);
        return this;
    }

    public static void setBackTex(TextureRegion backTex) {
        CardRenderObject.backTex = backTex;
    }
}
