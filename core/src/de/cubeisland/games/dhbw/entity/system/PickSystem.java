package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Picked;
import de.cubeisland.games.dhbw.entity.component.Transform;

public class PickSystem extends IteratingSystem {
    private DHBWGame game;

    public PickSystem(DHBWGame game) {
        super(Family.getFor(Picked.class, Transform.class));

        this.game = game;
    }
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        entity.add(new DestTransform(game.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), game.getCamera().project(new Vector3(0, 0, -100)).z)), new Quaternion(new Vector3(0, 1, 0), 0)));
    }
}
