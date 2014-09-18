package de.cubeisland.games.dhbw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DHBWGame extends ApplicationAdapter {
	DecalBatch batch;
	Texture img;
    PerspectiveCamera camera;
    Decal testDecal;
    Vector2 cardSpeed = new Vector2(0, 0);
    boolean cardPicked = false;
	
	@Override
	public void create () {
        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1;
        camera.far = 300;
        camera.position.set(0, 0, 1);

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

        cardPicked = Gdx.input.isTouched();

        if (cardPicked) {
            Vector3 dummy = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), camera.project(testDecal.getPosition().cpy()).z)).cpy().sub(testDecal.getPosition());
            cardSpeed.set(dummy.x, dummy.y).scl(0.5f);
            testDecal.getPosition().add(cardSpeed.x, cardSpeed.y, 0f);
            testDecal.setZ(-1.5f);
        } else {
            cardSpeed.set(0, 0);
            testDecal.setZ(-2);
        }
        testDecal.setRotation((float) Math.cos(Math.toRadians(cardSpeed.angle())) * cardSpeed.len() * 200f, -(float) Math.sin(Math.toRadians(cardSpeed.angle())) * cardSpeed.len() * 200f, 0);

        batch.add(testDecal);
		batch.flush();
	}
}
