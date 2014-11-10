package de.cubeisland.games.dhbw.entity.object;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.RenderObject;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * This object holds a image
 *
 * @author Phillip Schichtel
 */
public class ImageObject implements RenderObject {

    private final Decal image;

    public ImageObject(Texture texture) {
        image = Decal.newDecal(new TextureRegion(texture), true);
    }

    @Override
    public void render(DHBWGame game, Camera cam, Entity e, Transform t) {
        this.image.setPosition(t.getPosition());
        this.image.setRotation(t.getRotation());
        this.image.setScale(t.getScale());
        game.getDecalBatch().add(this.image);
    }

    @Override
    public boolean isWithin(Camera cam, float x, float y) {
        return false;
    }

    public Decal getDecal() {
        return this.image;
    }
}
