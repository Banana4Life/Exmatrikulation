package de.cubeisland.games.dhbw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.input.BoardInputProcessor;
import de.cubeisland.games.dhbw.input.GlobalInputProcessor;
import de.cubeisland.games.dhbw.input.InputMultiplexer;

public class DHBWGame extends ApplicationAdapter {
    private InputMultiplexer inputMultiplexer;
	private DecalBatch          batch;
    private PerspectiveCamera   camera;

    private Board board = new Board(this, new Vector3(0, 0, -100));
	
	@Override
	public void create () {
        inputMultiplexer = new InputMultiplexer(new BoardInputProcessor(board));
        Gdx.input.setInputProcessor(inputMultiplexer);

        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1;
        camera.far = 300;
        camera.position.set(0, 0, 1);

		batch = new DecalBatch(new CameraGroupStrategy(camera));

        Card.setBackTex(new TextureRegion(new Texture("back.png")));
        for (int i = 0; i < 5; i++) {
            this.board.addCard(new Card(this.board, new TextureRegion(new Texture("front.png")), new Vector3(20 * i, 0, -100)));
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();

        camera.update();

        board.render(delta);

		batch.flush();
	}

    public InputMultiplexer inputMultiplexer() {
        return this.inputMultiplexer;
    }

    public DecalBatch getDecalBatch() {
        return this.batch;
    }

    public PerspectiveCamera getCamera() {
        return this.camera;
    }
}
