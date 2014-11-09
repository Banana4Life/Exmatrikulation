package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim Adamek on 08.11.2014.
 */
public class ReactingState extends GameState {

    public static final short ID = 4;

    public static final int STARTCARDCOUNT = 3;
    private List<Entity> cardsInHand = new ArrayList<>();
    private List<Entity> cardsPlaying = new ArrayList<>();

    private Entity event;

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        //check if player presses the left mouse button, if not return false
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null  ) {
            if(cardsInHand.contains(e)) {
                //remember the card the player has clicked on
                cardsPlaying.add(e);
                e.getComponent(Transform.class).setPosition(new Vector3(-20 , 0, -100)).setRotation(new Quaternion(new Vector3(1, 0, 0), 0));

                //start the transition to CourseSelection
                return true;
            }
            if(cardsInHand.contains(e)){
                //remember the card the player has clicked on
                cardsPlaying.add(e);
                e.getComponent(Transform.class).setPosition(new Vector3(0 , 0, -100)).setRotation(new Quaternion(new Vector3(1, 0, 0), 0));

                //start the transition to CourseSelection
                return true;
            }
        }
        return false;
    }

    @Override
    public short id() {
        return ID;
    }

    public  List<Entity> getCardsInHand() {
        return cardsInHand;
    }

    public  void setCardsInHand(List<Entity> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public Entity getEvent() {
        return event;
    }

    public void setEvent(Entity event) {
        this.event = event;
    }
}
