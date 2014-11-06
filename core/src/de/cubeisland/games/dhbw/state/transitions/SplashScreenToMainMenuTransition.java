package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.resource.bag.Cards;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.MainMenu;
import life.banana4.util.resourcebags.ResourceBag;

import java.util.Arrays;
import java.util.List;

/**
 * Transition from SplashScreen to MainMenu. The deck with all the menu cards gets created and the first 3 cards are drawn.
 *
 * @author Jonas Dann
 * @author Time Adamek
 */
public class SplashScreenToMainMenuTransition extends StateTransition {
    //TODO choose actual gamemode
    //TODO remember card stack to go back to mainmenu

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        DHBWGame game = context.getGame();

        Entity deck = game.getEntityFactory().create(game.getResources().entities.deck);
        deck.getComponent(Transform.class).setPosition(new Vector3(40, 0, -150)).setRotation(new Quaternion(new Vector3(0, 1, 0), -90));
        deck.getComponent(Deck.class).setDestPos(new Vector3(0, 0, -150)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));

        game.getEngine().addEntity(deck);

        CardObject.setBackTex(new TextureRegion(new Texture("cards/cardback.png")));
        Cards cardPrefabs = game.getResources().cards;
        List<Card> cards = Arrays.asList(
                cardPrefabs.menufreemode,
                cardPrefabs.menustorymode,
                cardPrefabs.menubusinessinf,
                cardPrefabs.menubusinessad,
                cardPrefabs.menuappliedinf
        );
        Entity card;
        for (Card component : cards) {
            card = game.getEntityFactory().create(game.getResources().entities.card).add(component.copy());
            card.getComponent(Render.class).setObject(card.getComponent(Card.class).getCardObject());
            deck.getComponent(Deck.class).addCard(card);
            game.getEngine().addEntity(card);
        }
        for (int i = 0; i < 15; i++) {
            card = game.getEntityFactory().create(game.getResources().entities.card).add(cardPrefabs.dummy.copy());
            card.getComponent(Render.class).setObject(card.getComponent(Card.class).getCardObject());
            deck.getComponent(Deck.class).addCard(card);
            game.getEngine().addEntity(card);
        }
        for (int i = 0; i < 2; i++) {
            card = deck.getComponent(Deck.class).drawCard();
            ((MainMenu) context.getStateManager().getState(MainMenu.ID)).getCardStack().add(card);
            card.add(new DestTransform(new Vector3(-25 + 50 * i, 0, -150), new Quaternion(new Vector3(1, 0, 0), 0)));
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
//        as long as a Card contains a DestTransform the card is moving
        for (Entity card : context.getEngine().getEntitiesFor(Family.one(DestTransform.class).get())) {
            if (context.getGame().getResources().entities.card.matches(card)) {
                return false;
            }
        }
        return true;
    }
}
