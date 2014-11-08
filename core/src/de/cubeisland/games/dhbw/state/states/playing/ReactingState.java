package de.cubeisland.games.dhbw.state.states.playing;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim Adamek on 08.11.2014.
 */
public class ReactingState extends GameState {

    public static final short ID = 1;

    public static final int STARTCARDCOUNT = 3;
    private List<Entity> cardsInHand = new ArrayList<>();


    public  List<Entity> getCardsInHand() {
        return cardsInHand;
    }

    public  void setCardsInHand(List<Entity> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }
    @Override
    public short id() {
        return ID;
    }
}
