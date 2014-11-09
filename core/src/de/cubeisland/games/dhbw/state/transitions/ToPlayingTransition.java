package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.entity.object.DiceObject;
import de.cubeisland.games.dhbw.resource.bag.Cards;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.CourseSelection;
import de.cubeisland.games.dhbw.state.states.ReactingState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ToPlayingTransition extends StateTransition {
    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        DHBWGame game = context.getGame();

        Entity[] entities = game.getEngine().getEntitiesFor(Family.one(Card.class, Deck.class, CardHand.class).get()).toArray(Entity.class);
        for (Entity entity : entities) {
            game.getEngine().removeEntity(entity);
        }

        // construct deck
        Entity deck = game.getEntityFactory().create(game.getResources().entities.deck);
        deck.getComponent(Transform.class).setPosition(new Vector3(60, 0, -150)).setRotation(new Quaternion(new Vector3(0, 1, 0), -90));
        deck.getComponent(Deck.class).setDestPos(new Vector3(0, 0, -150)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));
        game.getEngine().addEntity(deck);

        //TODO get cards for the given semester
        Cards cardPrefabs = game.getResources().cards;
        List<Card> cards = new ArrayList<>();
        for(Card card: context.getGame().getResources().cards.getResources()){
            if(card.getType().name().equals("EVENT")){
                cards.add(card);
            }
        }

        int cardsInDeck = new Random().nextInt((10 - 5) + 1) + 5;
        Entity card;
        //for (Card component : cards) {
        for (int i=0;i<cardsInDeck;i++){
            int randomCard = new Random().nextInt(cards.size()-1);
            card = game.getEntityFactory().create(game.getResources().entities.card).add(cards.get(randomCard));
            card.getComponent(Render.class).setObject(card.getComponent(Card.class).getObject());
            deck.getComponent(Deck.class).addCard(card);
            game.getEngine().addEntity(card);
        }

        // construct card hand
        Entity cardHand = game.getEntityFactory().create(game.getResources().entities.cardhand);
        cardHand.getComponent(CardHand.class).setDestPos(new Vector3(0, -40, -150)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));
        cardHand.getComponent(Transform.class).setPosition(new Vector3(0, -40, -150)).setRotation(new Quaternion(new Vector3(1, 0, 0), 0));
        game.getEngine().addEntity(cardHand);

//        //draw cards for hand
//        for (int i = 0; i < ReactingState.STARTCARDCOUNT+1; i++) {
//            Entity entity = context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).drawCard();
//            cardHand.getComponent(CardHand.class).addCard(entity);
//        }
        //show the first event
        Entity event = context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).drawCard();
        event.add(new DestTransform(new Vector3(0, 20, -100), new Quaternion(new Vector3(1, 0, 0), 0)));
        ((ReactingState)context.getStateManager().getState(ReactingState.ID)).setEvent(event);

        //create dice
        Entity dice = game.getEntityFactory().create(game.getResources().entities.dice);
        dice.getComponent(Transform.class).setPosition(new Vector3(70, -50, -150));
        dice.getComponent(Render.class).setObject(new DiceObject());
        game.getEngine().addEntity(dice);
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
