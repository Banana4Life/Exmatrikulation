package de.cubeisland.games.dhbw;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Pickable;
import de.cubeisland.games.dhbw.entity.component.Transform;

import java.util.ArrayList;
import java.util.Iterator;

public class CardDeck implements Iterable<Entity> {
    private Board board;

    private Vector3             position;
    private Quaternion          rotation;
    private Vector3             destPos;
    private Quaternion          destRot;
    private ArrayList<Entity>   cards = new ArrayList<>();

    public CardDeck(Board board, Vector3 destPos) {
        this.board = board;

        this.destPos = destPos;
        this.destRot = new Quaternion(new Vector3(1, 0, 0), 0);
        this.position = destPos.cpy().add(30, 0, 0);
        this.rotation = new Quaternion(new Vector3(1, 0, 0), 90);
    }

    private void update() {
        for (int n = 0; n < Math.min(cards.size(), 6); n++) {
            cards.get(n).add(new DestTransform(position.cpy().add(n, 0, 0), rotation));
        }
    }

    public CardDeck drawCard() {
        if (cards.size() > 0) {
            Entity card = cards.remove(0);
            card.add(new DestTransform(card.getComponent(Transform.class)));
            card.getComponent(Transform.class).setPosition(destPos.cpy()).setRotation(destRot.cpy());
            card.add(new Pickable());
            this.board.addCard(card);
            this.update();
        }
        return this;
    }

    public CardDeck addCard(Entity card) {
        if (this.board.getGame().getResources().entities.card.matches(card)) {
            card.remove(Pickable.class);
            cards.add(card);
            this.update();
        }
        return this;
    }

    @Override
    public Iterator<Entity> iterator() {
        return cards.iterator();
    }
}
