package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

public class Deck extends Component implements Iterable<Entity> {
    private Vector3 destPos;
    private Quaternion destRot;
    private ArrayList<Entity> cards = new ArrayList<>();

    public Deck update(Transform transform) {
        for (int n = 0; n < Math.min(cards.size(), 6); n++) {
            cards.get(n).add(new DestTransform(transform.getPosition().cpy().add(n, 0, 0), transform.getRotation()));
        }
        return this;
    }

    public Entity drawCard() {
        if (cards.size() > 0) {
            Entity card = cards.remove(0);
            card.add(new DestTransform(destPos, destRot));
            card.add(new Pickable());
            return card;
        }
        return null;
    }

    public Deck addCard(Entity card) {
        card.remove(Pickable.class);
        cards.add(card);
        return this;
    }

    public Deck setDestPos(Vector3 destPos) {
        this.destPos = destPos;
        return this;
    }

    public Deck setDestRot(Quaternion destRot) {
        this.destRot = destRot;
        return this;
    }

    @Override
    public Iterator<Entity> iterator() {
        return cards.iterator();
    }
}
