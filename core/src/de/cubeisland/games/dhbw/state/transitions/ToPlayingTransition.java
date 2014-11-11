package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.entity.object.DiceObject;
import de.cubeisland.games.dhbw.entity.object.TextObject;
import de.cubeisland.games.dhbw.entity.object.ToMenuObject;
import de.cubeisland.games.dhbw.resource.bag.Cards;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.DecidingState;
import de.cubeisland.games.dhbw.state.states.MainMenu;
import de.cubeisland.games.dhbw.state.states.NextSemester;
import de.cubeisland.games.dhbw.state.states.ReactingState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The transition to reacting state
 *
 * @author Tim Adamek
 * @author Jonas Dann
 * @author Andreas Geis
 */
public class ToPlayingTransition extends StateTransition {

    public static final String EVENT = "EVENT";
    public static final String ITEM = "ITEM";

    private List<Card> copyOfItemCads = new ArrayList<>();
    private List<Card> copyOfEventCads = new ArrayList<>();

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        DHBWGame game = context.getGame();
        //check if the player chose the story or free mode and set the maximum number of semesters
        if (((MainMenu) context.getStateManager().getState(MainMenu.ID)).getPickedcard().getComponent(Card.class).getId().toLowerCase().contains("free")) {
            ((DecidingState) context.getStateManager().getState(DecidingState.ID)).setMaxSemester(1);
        } else {
            ((DecidingState) context.getStateManager().getState(DecidingState.ID)).setMaxSemester(6);
        }

        Entity[] entities = game.getEngine().getEntitiesFor(Family.one(Card.class, Deck.class, CardHand.class).get()).toArray(Entity.class);
        for (Entity entity : entities) {
            game.getEngine().removeEntity(entity);
        }

        Entity card;

        // construct event card deck
        Entity eventDeck = game.getEntityFactory().create(game.getResources().entities.deck);
        eventDeck.getComponent(Transform.class).setPosition(new Vector3(90, 40, -150)).setRotation(new Quaternion(new Vector3(0, 1, 0), 180));
        eventDeck.getComponent(Deck.class).setDestPos(new Vector3(0, 20, -100)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));
        game.getEngine().addEntity(eventDeck);
        if (ReactingState.class == destination.getClass()) {
            ((ReactingState) destination).setEventDeck(eventDeck);
        }

        List<Card> eventCards = new ArrayList<>();
        for (Card eventCard : context.getGame().getResources().cards.getResources()) {
            if (eventCard.getType().name().equals(EVENT)) {
                eventCards.add(eventCard);
                copyOfEventCads.add(eventCard);
            }
        }

        int cardsInDeck = new Random().nextInt((10 - 5) + 1) + 5;
        createDeck(eventDeck, context.getGame(), true, cardsInDeck);

        //cinstruct the itemCardDeck
        Entity itemDeck = game.getEntityFactory().create(game.getResources().entities.deck);
        itemDeck.getComponent(Transform.class).setPosition(new Vector3(90, 0, -150)).setRotation(new Quaternion(new Vector3(0, 1, 0), 180));
        itemDeck.getComponent(Deck.class).setDestPos(new Vector3(200, 60, -150)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));
        game.getEngine().addEntity(itemDeck);
        if (ReactingState.class == destination.getClass()) {
            ((ReactingState) destination).setItemDeck(itemDeck);
        }

        List<Card> itemCards = new ArrayList<>();
        for (Card itemCard : context.getGame().getResources().cards.getResources()) {
            if (itemCard.getType().name().equals(ITEM)) {
                itemCards.add(itemCard);
                copyOfItemCads.add(itemCard);
            }
        }
        createDeck(itemDeck, context.getGame(), false, 60);

        // construct card hand
        Entity cardHand = game.getEntityFactory().create(game.getResources().entities.cardhand);
        cardHand.getComponent(CardHand.class).setDestPos(new Vector3(0, -40, -150)).setDestRot(new Quaternion(new Vector3(1, 0, 0), 0));
        cardHand.getComponent(Transform.class).setPosition(new Vector3(0, -40, -150)).setRotation(new Quaternion(new Vector3(1, 0, 0), 0));
        game.getEngine().addEntity(cardHand);

        //draw cards for hand
        for (int i = 0; i < ReactingState.STARTCARDCOUNT; i++) {
            Entity entity = itemDeck.getComponent(Deck.class).drawCard();
            cardHand.getComponent(CardHand.class).addCard(entity);
        }

        //show the first event
        Entity event = context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).drawCard();
        if (ReactingState.class == destination.getClass()) {
            ((ReactingState) destination).setEvent(event);
        }

        //create dice
        Entity dice = game.getEntityFactory().create(game.getResources().entities.dice);
        dice.getComponent(Transform.class).setPosition(new Vector3(68, -35, -100));
        dice.getComponent(Render.class).setObject(new DiceObject());
        game.getEngine().addEntity(dice);

        Entity toMenu = game.getEntityFactory().create(game.getResources().entities.tomenu);
        toMenu.getComponent(Transform.class).setPosition(new Vector3(68, -25, -100));
        toMenu.getComponent(Render.class).setObject(new ToMenuObject());
        game.getEngine().addEntity(toMenu);

        // create calkboard
        Entity status = game.getEntityFactory().createStatus(TextObject.UPPER_LEFT.cpy().add(5, -5));
        game.getEngine().addEntity(status);
        Entity calkboard = game.getEntityFactory().createImage("images/calkboard.png", new Vector3(-60.3f, 30.85f, -100), 0.11f);
        game.getEngine().addEntity(calkboard);
        ((ReactingState) destination).setCalkBoard(calkboard);
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

    /**
     * this method creates the card deck
     * the cards are random, but the probability of them appearing is reduced every time
     *
     * @param deck      the deck which should be created
     * @param game      the game
     * @param forEvents indicates if the deck that should be created is the event or the character deck
     * @param decksize  the size the deck should have
     */
    private void createDeck(Entity deck, DHBWGame game, boolean forEvents, int decksize) {
        //the copyOf<...>Cards is used to avoid modifying the original cards, that allows that the altered rarity is not carried over between multiple games

        //the idea ist to add the cads multiply times to a list, depending on the rarity of the card, the card is put to the deck more or less often,
        //and then picking aa random card from that list
        //after that the rarity of thr card is reduced
        Entity e;
        List<Card> cardRarity = new ArrayList<>();

        for (int i = 0; i < decksize; i++) {
            if (forEvents) {
                for (int cardCounter = 0; cardCounter < copyOfEventCads.size(); cardCounter++) {
                    for (int count = 0; count < copyOfEventCads.get(cardCounter).getRarity(); count++) {
                        cardRarity.add(copyOfEventCads.get(cardCounter));
                    }
                }
            } else {
                for (int cardCounter = 0; cardCounter < copyOfItemCads.size(); cardCounter++) {
                    for (int count = 0; count < copyOfItemCads.get(cardCounter).getRarity(); count++) {
                        cardRarity.add(copyOfItemCads.get(cardCounter));
                    }
                }
            }

            //generates the index of the card in the list
            int randomCard = new Random().nextInt(cardRarity.size() - 1);
            e = game.getEntityFactory().create(game.getResources().entities.card).add(cardRarity.get(randomCard));
            e.getComponent(Render.class).setObject(new CardObject(e.getComponent(Card.class).getObject()));
            deck.getComponent(Deck.class).addCard(e);
            game.getEngine().addEntity(e);

            int position = 0;
            //decide if the card has to be searched in the list of event or item cards
            if (forEvents) {
                for (int count = 0; count < copyOfEventCads.size(); count++) {
                    if (e.getComponent(Card.class).getId().equals(copyOfEventCads.get(count).getId())) {
                        position = count;
                        break;
                    }
                }
                //the rarity is reduced, the +1 is to prevent the card from never appearing again
                copyOfEventCads.get(position).setRarity(Math.round(copyOfEventCads.get(position).getRarity() / 2) + 1);
            } else {
                for (int count = 0; count < copyOfItemCads.size(); count++) {
                    if (e.getComponent(Card.class).getId().equals(copyOfItemCads.get(count).getId())) {
                        position = count;
                        break;
                    }
                }
                //the rarity is reduced, the +1 is to prevent the card from never appearing again
                copyOfItemCads.get(position).setRarity(Math.round(copyOfItemCads.get(position).getRarity() / 2) + 1);
            }
            //remove the cards from the list, because it is calculated again
            while (cardRarity.size() > 0) {
                cardRarity.remove(0);
            }
        }

    }
}
