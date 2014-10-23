package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Model;
import de.cubeisland.games.dhbw.util.modelobject.ModelObject3D;

public class RenderObject3D extends RenderObject {
    @Override
    public void render(PerspectiveCamera cam, Entity e, DHBWGame game) {
        ModelInstance instance = ((ModelObject3D)e.getComponent(Model.class).getModelObject()).getInstance();

        ModelBatch batch = game.getModelBatch();

        batch.begin(cam);
        batch.render(instance, game.getEnvironment());
        batch.end();
    }
}
