package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.RenderObject;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * This object holds a background
 */
public class BackgroundObject implements RenderObject {

    private final Decal background;

    public BackgroundObject(Texture texture) {
        background = Decal.newDecal(new TextureRegion(texture));
        background.setPosition(new Vector3(0, 0, -298));
        background.setRotation(new Quaternion(Vector3.Zero, 0));
        background.setScale(.344f);
    }

    @Override
    public void render(DHBWGame game, Camera cam, Entity e, Transform transform) {
        game.getDecalBatch().add(this.background);
    }

    @Override
    public boolean isWithin(Camera cam, float x, float y) {
        return true;
    }
}
