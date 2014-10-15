package de.cubeisland.games.dhbw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.input.BoardInputProcessor;
import de.cubeisland.games.dhbw.input.InputMultiplexer;
import de.cubeisland.games.dhbw.resource.DHBWResources;
import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.state.states.*;
import de.cubeisland.games.dhbw.state.transitions.DummyTransition;
import de.cubeisland.games.dhbw.util.ClassConverter;

public class DHBWGame extends ApplicationAdapter {
    private Reflector           reflector;
    private DHBWResources       resources;
    private StateManager        stateManager;
    private InputMultiplexer    inputMultiplexer;
	private DecalBatch          batch;
    private ModelBatch          modelBatch;
    private Environment         environment;
    private PerspectiveCamera   camera;

    private Board board = new Board(this, new Vector3(0, 0, -100));
	
	@Override
	public void create () {
        reflector = new Reflector();
        reflector.getDefaultConverterManager().registerConverter(Class.class, new ClassConverter());
        resources = new DHBWResources(reflector);
        resources.build();

        this.stateManager = new StateManager(this);
        this.stateManager
                .addState(new SplashScreen())
                .addState(new MainMenu())
                .addState(new CharacterSelection())
                .addState(new DifficultySelection())
                .addState(new Playing())
                .addState(new Paused())
                .addTransition(StateManager.NO_STATE,   SplashScreen.ID,        DummyTransition.INSTANCE)
                .addTransition(SplashScreen.ID,         MainMenu.ID,            DummyTransition.INSTANCE)
                .addTransition(MainMenu.ID,             CharacterSelection.ID,  DummyTransition.INSTANCE)
                .addTransition(CharacterSelection.ID,   MainMenu.ID,            DummyTransition.INSTANCE)
                .addTransition(CharacterSelection.ID,   DifficultySelection.ID, DummyTransition.INSTANCE)
                .addTransition(DifficultySelection.ID,  CharacterSelection.ID,  DummyTransition.INSTANCE)
                .addTransition(DifficultySelection.ID,  MainMenu.ID,            DummyTransition.INSTANCE)
                .addTransition(DifficultySelection.ID,  Playing.ID,             DummyTransition.INSTANCE)
                .addTransition(Playing.ID,              Paused.ID,              DummyTransition.INSTANCE)
                .addTransition(Paused.ID,               Playing.ID,             DummyTransition.INSTANCE)
                .addTransition(Paused.ID,               MainMenu.ID,            DummyTransition.INSTANCE)
                .start();

        inputMultiplexer = new InputMultiplexer(new BoardInputProcessor(board));
        Gdx.input.setInputProcessor(inputMultiplexer);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1;
        camera.far = 300;
        camera.position.set(0, 0, 1);

		batch = new DecalBatch(new CameraGroupStrategy(camera));
        modelBatch = new ModelBatch();

        CardDeck deck = new CardDeck(this.board, new Vector3(0, 0, -100));
        this.board.addDeck(deck);
        Card.setBackTex(new TextureRegion(new Texture("back.png")));
        for (int i = 0; i < 5; i++) {
            deck.addCard(new Card(this.board, new TextureRegion(new Texture("front.png")), new Vector3(20 * i, 0, -100)));
        }

        this.board.addDice(new Dice(board));
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

    public ModelBatch getModelBatch() {
        return modelBatch;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public PerspectiveCamera getCamera() {
        return this.camera;
    }
}
