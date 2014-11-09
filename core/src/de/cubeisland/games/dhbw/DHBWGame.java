package de.cubeisland.games.dhbw;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.EntityFactory;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.system.*;
import de.cubeisland.games.dhbw.input.GlobalInputProcessor;
import de.cubeisland.games.dhbw.input.InputMultiplexer;
import de.cubeisland.games.dhbw.resource.DHBWResources;
import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.state.states.*;
import de.cubeisland.games.dhbw.state.transitions.*;
import de.cubeisland.games.dhbw.util.ActionTuple;
import de.cubeisland.games.dhbw.util.CardTypeConverter;
import de.cubeisland.games.dhbw.util.ClassConverter;
import de.cubeisland.games.dhbw.util.SubjectTypeConverter;

import static de.cubeisland.games.dhbw.state.StateManager.EndState;
import static de.cubeisland.games.dhbw.state.StateManager.StartState;

public class DHBWGame extends ApplicationAdapter {
    private DHBWResources resources;
    private StateManager stateManager;
    private InputMultiplexer inputMultiplexer;
    private DecalBatch decalBatch;
    private SpriteBatch spriteBatch;
    private EntityFactory entityFactory;
    private Engine engine;

    private PlayerCharacter character;

    @Override
    public void create() {
        Reflector reflector = new Reflector();
        reflector.getDefaultConverterManager().registerConverter(Class.class, new ClassConverter());
        reflector.getDefaultConverterManager().registerConverter(CardPrefab.CardType.class, new CardTypeConverter());
        reflector.getDefaultConverterManager().registerConverter(CardPrefab.SubjectType.class, new SubjectTypeConverter());
        reflector.getDefaultConverterManager().registerConverter(ActionTuple.class, new ActionTuple.ActionConverter());

        resources = new DHBWResources(reflector);
        resources.build();
        entityFactory = new EntityFactory(resources.entities);

        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspectiveCamera.near = 1;
        perspectiveCamera.far = 300;
        perspectiveCamera.position.set(0, 0, 1);

        OrthographicCamera orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Entity cameraEntity = entityFactory.create(resources.entities.camera);
        Camera camera = cameraEntity.getComponent(Camera.class);
        camera.setOrthographic(orthographicCamera);
        camera.setPerspective(perspectiveCamera);

        decalBatch = new DecalBatch(new CameraGroupStrategy(perspectiveCamera));
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        engine = new Engine();
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(camera, this));
        engine.addSystem(new ControlSystem());
        engine.addSystem(new DeckSystem());
        engine.addSystem(new PickSystem(perspectiveCamera));
        engine.addSystem(new CameraSystem());
        engine.addSystem(new CardHandSystem());
        engine.addSystem(new DiceSystem());
        engine.addSystem(new MusicSystem(resources.songs.getResources()));

        inputMultiplexer = new InputMultiplexer(new GlobalInputProcessor(perspectiveCamera, engine, this.stateManager));
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.character = new PlayerCharacter();

        this.stateManager = new StateManager(this, engine, camera, inputMultiplexer);
        this.stateManager
                .addState(new SplashScreen())
                .addState(new MainMenu())
                .addState(new CourseSelection())
                .addState(new ReactingState())
                .addState(new GameLostState())
                .addState(new GameWonState())
                .addState(new DecidingState())
                .addState(new Paused())
                .addTransition(StartState.ID, SplashScreen.ID, NOPTransition.INSTANCE)
                .addTransition(SplashScreen.ID, MainMenu.ID, new ScreenToMainMenuTransition())
                .addTransition(MainMenu.ID, CourseSelection.ID, new MergeCardsAndMoveToCorner())
                .addTransition(MainMenu.ID, EndState.ID, NOPTransition.INSTANCE)
                .addTransition(CourseSelection.ID,      MainMenu.ID,                new BackInMenusTransition())
                .addTransition(CourseSelection.ID,      ReactingState.ID,           new ToPlayingTransition())
                .addTransition(ReactingState.ID,        DecidingState.ID,           NOPTransition.INSTANCE)
                .addTransition(ReactingState.ID,        Paused.ID,                  NOPTransition.INSTANCE)
                .addTransition(DecidingState.ID,        Paused.ID,                  NOPTransition.INSTANCE)
                .addTransition(DecidingState.ID,        ReactingState.ID,           new NextEventTransition())
                .addTransition(DecidingState.ID,        GameLostState.ID,           new GameEndTransition())
                .addTransition(DecidingState.ID,        GameWonState.ID,            new GameWonTransition())
                .addTransition(GameWonState.ID,         MainMenu.ID,                new ScreenToMainMenuTransition())
                .addTransition(GameLostState.ID,        MainMenu.ID,                new ScreenToMainMenuTransition())
                .addTransition(Paused.ID,               ReactingState.ID,           NOPTransition.INSTANCE)
                .addTransition(Paused.ID,               DecidingState.ID,           NOPTransition.INSTANCE)
                .addTransition(Paused.ID,               MainMenu.ID,                NOPTransition.INSTANCE)
                .start();

        engine.addEntity(cameraEntity);

        engine.addEntity(entityFactory.createImage("images/background.png", new Vector3(0, 0, -298), .344f));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        engine.update(delta);
        stateManager.update(delta);
    }

    public InputMultiplexer getInputMultiplexer(){
        return this.inputMultiplexer;
    }

    public DecalBatch getDecalBatch() {
        return this.decalBatch;
    }

    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

    public DHBWResources getResources() {
        return resources;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public Engine getEngine() {
        return engine;
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

    public void exit() {
        Gdx.app.exit();
    }
}