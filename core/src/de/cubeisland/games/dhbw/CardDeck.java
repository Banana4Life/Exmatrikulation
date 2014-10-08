package de.cubeisland.games.dhbw;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

public class CardDeck implements Iterable<Card> {
    private Board board;

    private Vector3             position;
    private Quaternion          rotation;
    private Vector3             destPos;
    private Quaternion          destRot;
    private ArrayList<Card>     cards = new ArrayList<>();

    public CardDeck(Board board, Vector3 destPos) {
        this.board = board;

        this.destPos = destPos;
        this.destRot = new Quaternion(new Vector3(1, 0, 0), 0);
        this.position = destPos.cpy().add(30, 0, 0);
        this.rotation = new Quaternion(new Vector3(1, 0, 0), 90);
    }

    public void render(float delta) {
        for (Card card : cards) {
            card.render(delta);
        }
    }

    private void update() {
        for (int n = 0; n < Math.min(cards.size(), 6); n++) {
            cards.get(n).setDestPos(position.cpy().add(n, 0, 0)).setDestRot(rotation);
        }
    }

    public CardDeck drawCard() {
        if (cards.size() > 0) {
            this.board.addCard(cards.remove(0).setDestPos(destPos.cpy()).setDestRot(destRot.cpy()).setPickable(true));
            this.update();
        }
        return this;
    }

    public CardDeck addCard(Card card) {
        cards.add(card.setPickable(false));
        this.update();
        return this;
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}
