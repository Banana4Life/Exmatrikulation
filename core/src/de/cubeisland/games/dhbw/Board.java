package de.cubeisland.games.dhbw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Board {
    private DHBWGame game;

    private Vector3             position;
    private Card                pickedCard;
    private ArrayList<Card>     cards = new ArrayList<>();
    private ArrayList<CardDeck> decks = new ArrayList<>();
    private ArrayList<Dice>     dices = new ArrayList<>();

    public Board(DHBWGame game, Vector3 position) {
        this.game = game;
        this.position = position;
    }

    public void render(float delta) {
        for (Card card : cards) {
            card.render(delta);
        }
        for (CardDeck deck : decks) {
            deck.render(delta);
        }
        for (Dice dice : dices) {
            dice.render(delta);
        }
    }

    public Board addCard(Card card) {
        cards.add(card.setPickable(true));
        return this;
    }

    public void pickCard(float screenX, float screenY) {
        if (pickedCard == null) {
            for (Card card : cards) {
                if (card.isPickable() && card.isClickOnProjectedCard(screenX, Gdx.graphics.getHeight() - screenY)) {
                    pickedCard = card;
                }
            }
        }
        dragCard(screenX, screenY);
    }

    public void dragCard(float screenX, float screenY) {
        if (pickedCard != null) {
            pickedCard.setDestPos(game.getCamera().unproject(new Vector3(screenX, screenY, game.getCamera().project(new Vector3(position.x, position.y, position.z + 10)).z)));
            pickedCard.getDestPos().z = position.z + 10;
        }
    }

    public void releaseCard() {
        if (pickedCard != null) {
            pickedCard.getDestPos().z = position.z;
        }
        pickedCard = null;
    }

    public DHBWGame getGame() {
        return game;
    }
}
