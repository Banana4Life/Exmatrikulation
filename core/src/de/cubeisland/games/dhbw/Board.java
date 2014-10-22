package de.cubeisland.games.dhbw;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.util.renderobject.CardRenderObject;

import java.util.ArrayList;

public class Board {
    private DHBWGame game;

    private Vector3             position;
    private Entity              pickedCard;
    private ArrayList<Entity>   cards = new ArrayList<>();
    private ArrayList<CardDeck> decks = new ArrayList<>();
    private ArrayList<Dice>     dices = new ArrayList<>();

    public Board(DHBWGame game, Vector3 position) {
        this.game = game;
        this.position = position;
    }

    public void render(float delta) {
        for (Dice dice : dices) {
            dice.render(delta);
        }
    }

    public Board addDice(Dice dice) {
        dices.add(dice);
        return this;
    }

    public Board addCard(Entity card) {
        if (this.getGame().getResources().entities.card.matches(card)) {
            cards.add(card);
        }
        return this;
    }

    public Board addDeck(CardDeck deck) {
        decks.add(deck);
        return this;
    }

    public void pickCard(float screenX, float screenY) {
        if (pickedCard == null) {
            for (Entity card : cards) {
                if (card.getComponent(Pickable.class) != null && card.getComponent(CardModel.class).isClickOnProjectedCard(this.getGame().getCamera(), screenX, Gdx.graphics.getHeight() - screenY)) {
                    pickedCard = card;
                }
            }
        }
        dragCard(screenX, screenY);
    }

    public void dragCard(float screenX, float screenY) {
        if (pickedCard != null) {
            pickedCard.add(new DestTransform(game.getCamera().unproject(new Vector3(screenX, screenY, game.getCamera().project(new Vector3(position.x, position.y, position.z + 10)).z)), pickedCard.getComponent(Transform.class).getRotation()));
            pickedCard.getComponent(DestTransform.class).getPosition().z = position.z + 10;
        }
    }

    public void releaseCard() {
        if (pickedCard != null) {
            pickedCard.getComponent(DestTransform.class).getPosition().z = position.z;
        }
        pickedCard = null;
    }

    public DHBWGame getGame() {
        return game;
    }

    public ArrayList<CardDeck> getDecks() {
        return decks;
    }
}
