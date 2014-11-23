package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

/**
 * this is the state between the end of one semester and
 * the beginning of the new semester
 *
 * @author Tim Adamek
 */
public class NextSemester extends GameState {
    public static final short ID = 6;
    private Entity semester;

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        context.getEngine().removeEntity(semester);
        context.transitionTo(ReactingState.ID);
        return true;
    }

    @Override
    public short id() {
        return ID;
    }

    public Entity getSemester() {
        return semester;
    }

    public void setSemester(Entity semester) {
        this.semester = semester;
    }
}
