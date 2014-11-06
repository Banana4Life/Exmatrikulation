package de.cubeisland.games.dhbw;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.engine.reflect.Reflector;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.EntityFactory;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.entity.system.*;
import de.cubeisland.games.dhbw.input.GlobalInputProcessor;
import de.cubeisland.games.dhbw.input.InputMultiplexer;
import de.cubeisland.games.dhbw.resource.DHBWResources;
import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.state.states.*;
import de.cubeisland.games.dhbw.state.transitions.*;
import de.cubeisland.games.dhbw.util.CardTypeConverter;
import de.cubeisland.games.dhbw.util.ClassConverter;
import de.cubeisland.games.dhbw.util.SubjectTypeConverter;

import static de.cubeisland.games.dhbw.state.StateManager.EndState;
import static de.cubeisland.games.dhbw.state.StateManager.StartState;

public class DHBWGame extends ApplicationAdapter {
    private DHBWResources       resources;
    private StateManager        stateManager;
    private InputMultiplexer    inputMultiplexer;
    private DecalBatch          batch;
    private Environment         environment;
    private EntityFactory       entityFactory;
    private Engine              engine;

    @Override
    public void create () {
        Reflector reflector = new Reflector();
        reflector.getDefaultConverterManager().registerConverter(Class.class, new ClassConverter());
        reflector.getDefaultConverterManager().registerConverter(CardPrefab.CardType.class, new CardTypeConverter());
        reflector.getDefaultConverterManager().registerConverter(CardPrefab.SubjectType.class, new SubjectTypeConverter());
        resources = new DHBWResources(reflector);
        resources.build();
        entityFactory = new EntityFactory();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        PerspectiveCamera camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1;
        camera.far = 300;
        camera.position.set(0, 0, 1);

        batch = new DecalBatch(new CameraGroupStrategy(camera));

        engine = new Engine();
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(camera, this));
        engine.addSystem(new ControlSystem());
        engine.addSystem(new DeckSystem());
        engine.addSystem(new PickSystem(camera));
        engine.addSystem(new CameraSystem());

        inputMultiplexer = new InputMultiplexer(new GlobalInputProcessor(camera, engine, this.stateManager));
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.stateManager = new StateManager(this, engine, camera, inputMultiplexer);
        this.stateManager
                .addState(new SplashScreen())
                .addState(new MainMenu())
                .addState(new CourseSelection())
                .addState(new Playing())
                .addState(new Paused())
                .addTransition(StartState.ID,               SplashScreen.ID,        DummyTransition.INSTANCE)
                .addTransition(SplashScreen.ID,             MainMenu.ID,            new SplashScreenToMainMenuTransition())
                .addTransition(MainMenu.ID,                 CourseSelection.ID,     MergeCardsAndMoveToCorner.INSTANCE)
                .addTransition(MainMenu.ID,                 EndState.ID,            DummyTransition.INSTANCE)
                .addTransition(CourseSelection.ID,          MainMenu.ID,            BackInMenusTransition.INSTANCE)
                .addTransition(CourseSelection.ID,          Playing.ID,             DummyTransition.INSTANCE)
                .addTransition(Playing.ID,                  Paused.ID,              DummyTransition.INSTANCE)
                .addTransition(Paused.ID,                   Playing.ID,             DummyTransition.INSTANCE)
                .addTransition(Paused.ID,                   MainMenu.ID,            DummyTransition.INSTANCE)
                .start();

        Entity cameraEntity = entityFactory.create(resources.entities.camera);
        cameraEntity.getComponent(Camera.class).set(camera);
        engine.addEntity(cameraEntity);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        engine.update(delta);
        stateManager.update(delta);
    }

    public DecalBatch getDecalBatch() {
        return this.batch;
    }

    public Environment getEnvironment() {
        return environment;
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

    public void exit() {
        Gdx.app.exit();
    }
}
