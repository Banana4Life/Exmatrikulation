package de.cubeisland.games.dhbw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;

public class DHBWGame extends ApplicationAdapter {
	DecalBatch batch;
	Texture img;
    PerspectiveCamera camera;
    Decal testDecal;
	
	@Override
	public void create () {
        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1;
        camera.far = 300;
        camera.position.set(0, 0, 5);

		batch = new DecalBatch(new CameraGroupStrategy(camera));

		img = new Texture("badlogic.jpg");
        testDecal = Decal.newDecal(1, 1, new TextureRegion(img));
        testDecal.setPosition(0, 0, -1);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();

        testDecal.rotateX(0.5f);

        batch.add(testDecal);
		batch.flush();
	}
}
