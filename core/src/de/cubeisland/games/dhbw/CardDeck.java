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
    private ArrayList<Card>     cards = new ArrayList<>();

    private static final Quaternion destRot = new Quaternion(new Vector3(1, 0, 0), 90);

    public CardDeck(Board board, Vector3 destPos) {
        this.board = board;

        this.destPos = destPos;
        this.position = destPos.cpy().add(10, 0, 0);
        this.rotation = new Quaternion(new Vector3(1, 0, 0), 0);
    }

    public void render(float delta) {
        for (Card card : cards) {
            card.render(delta);
        }
    }

    private void update() {
        for (int n = 0; n < Math.min(cards.size(), 6); n++) {
            cards.get(n).setDestPos(destPos.cpy().add(n, 0, 0)).setDestRot(destRot);
        }
    }

    public CardDeck drawCard() {
        if (cards.size() > 0) {
            this.board.addCard(cards.remove(0).setDestPos(position.cpy()).setDestRot(rotation.cpy()).setPickable(true));
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
