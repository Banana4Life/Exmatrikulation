package de.cubeisland.games.dhbw.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import de.cubeisland.games.dhbw.Board;

public class BoardInputProcessor implements InputProcessor {
    private Board board;

    public BoardInputProcessor(Board board) {
        this.board = board;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            board.getDecks().get(0).drawCard();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            board.pickCard(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            board.releaseCard();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        board.dragCard(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
