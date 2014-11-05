package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

public class MenusToPlayingTransition extends StateTransition {
    @Override
    public void begin(StateContext context) {
        super.begin(context);

        DHBWGame game = context.getGame();

        Entity deck = game.getEntityFactory().create(game.getResources().entities.deck);
        deck.getComponent(Transform.class).setPosition(new Vector3(40, 0, -100)).setRotation(new Quaternion(new Vector3(0, 1, 0), -90));
        deck.getComponent(Deck.class).setDestPos(new Vector3(0, 0, -100)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));

        game.getEngine().addEntity(deck);

        CardObject.setBackTex(new TextureRegion(new Texture("cards/cardback.png")));
        for (int i = 0; i < 15; i++) {
            Entity card = game.getEntityFactory().create(game.getResources().entities.card);
            card.getComponent(Transform.class).setPosition(new Vector3(20 * i, 0, -100)).setRotation(new Quaternion(new Vector3(1, 0, 0), 0));
            card.getComponent(Render.class).setObject(new CardObject(new TextureRegion(new Texture("cards/cardfront.png"))));

            game.getEngine().addEntity(card);
            deck.getComponent(Deck.class).addCard(card);
        }
    }

    @Override
    public boolean transition(StateContext context, float delta) {
        for (Entity entity : context.getEngine().getEntitiesFor(Family.one(DestTransform.class).get())) {
            if (context.getGame().getResources().entities.card.matches(entity)) {
                return false;
            }
        }
        return true;
    }
}
