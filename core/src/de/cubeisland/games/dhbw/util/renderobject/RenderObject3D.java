package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Model;
import de.cubeisland.games.dhbw.util.modelobject.ModelObject3D;

public class RenderObject3D extends RenderObject {
    @Override
    public void render(Entity e, DHBWGame game) {
        ModelInstance instance = ((ModelObject3D)e.getComponent(Model.class).getModelObject()).getInstance();

        game.getModelBatch().begin(game.getCamera());
        game.getModelBatch().render(instance, game.getEnvironment());
        game.getModelBatch().end();
    }
}
