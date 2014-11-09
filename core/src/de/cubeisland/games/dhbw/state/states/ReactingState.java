package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
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

    private final float DEFAULTHANDPOSITION_X =0f;
    private final float DEFAULTHANDPOSITION_Y =-30f;
    private final float DEFAULTHANDPOSITION_Z =-150f;
    private final int GAPFORHAND =40;
    private final int GAPFACTORFORHAND =3;
    private final int MOVELEFTFORHAND =10;

    private final float DEFAULTPLAYEDPOSITION_X =0f;
    private final float DEFAULTPLAYEDPOSITION_Y =0f;
    private final float DEFAULTPLAYEDPOSITION_Z =-100f;
    private final int GAPFORPLAYED =40;
    private final int GAPFACTORFORPLAYED =3;
    private final int MOVELEFTFORPLAYED =10;


    private final Quaternion defaultRotation = new Quaternion(new Vector3(0,0,1),0);



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

                //remove the card from the hand
                cardsInHand.remove(e);

                sortCardsInHand();
                sortPlayedCads();
                return true;
            }
            if(cardsPlaying.contains(e)){
                //remember the card the player has clicked on
                cardsInHand.add(e);
                e.getComponent(Transform.class).setPosition(new Vector3(0 , 0, -100)).setRotation(new Quaternion(new Vector3(1, 0, 0), 0));

                //remove the card from the cards which are played
                cardsPlaying.remove(e);

                sortCardsInHand();
                sortPlayedCads();
                return true;
            }
        }
        return false;
    }


    private void sortPlayedCads(){
        for (int i=0;i<cardsPlaying.size();i++){
            //TODO fixed start x,y,z
            float x = DEFAULTPLAYEDPOSITION_X;
            float y = DEFAULTPLAYEDPOSITION_Y;
            float z = DEFAULTPLAYEDPOSITION_Z;
            x=x-cardsPlaying.size()* MOVELEFTFORPLAYED +i*(GAPFORPLAYED -cardsPlaying.size()* GAPFACTORFORPLAYED);
            cardsPlaying.get(i).add(new DestTransform(new Vector3(x,y,z),defaultRotation));
        }
    }

    private void sortCardsInHand(){
        for (int i=0;i<cardsInHand.size();i++){
            //TODO fixed start x,y,z
            float x = DEFAULTHANDPOSITION_X;
            float y = DEFAULTHANDPOSITION_Y;
            float z = DEFAULTHANDPOSITION_Z;
            x=x-cardsInHand.size()* MOVELEFTFORHAND +i*(GAPFORHAND -cardsInHand.size()* GAPFACTORFORHAND);
            cardsInHand.get(i).add(new DestTransform(new Vector3(x,y,z),defaultRotation));
        }
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
