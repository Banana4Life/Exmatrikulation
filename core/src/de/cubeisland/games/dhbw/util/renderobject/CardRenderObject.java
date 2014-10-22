package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.CardModel;

public class CardRenderObject extends RenderObject {
    @Override
    public void render(Entity e, DHBWGame game) {
        CardModel cardModel = e.getComponent(CardModel.class);

        game.getDecalBatch().add(cardModel.getFrontDecal());
        game.getDecalBatch().add(cardModel.getBackDecal());
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
            board.getGCardRenderObjectame().getDecalBatch().add(backDecal);
        }
        */
    }
}
