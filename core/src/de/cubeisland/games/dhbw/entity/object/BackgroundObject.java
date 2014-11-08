package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
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
        background.setScale(.344f);
    }

    @Override
    public void render(DHBWGame game, Camera cam, Entity e, Transform transform) {
        this.background.setPosition(transform.getPosition());
        this.background.setRotation(transform.getRotation());
        game.getDecalBatch().add(this.background);
    }

    @Override
    public boolean isWithin(Camera cam, float x, float y) {
        return true;
    }
}
