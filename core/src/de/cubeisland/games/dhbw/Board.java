package de.cubeisland.games.dhbw;

import java.util.ArrayList;

public class Board {
    private DHBWGame game;

    private ArrayList<Card>     cards = new ArrayList<>();
    private ArrayList<CardDeck> decks = new ArrayList<>();
    private ArrayList<Dice>     dices = new ArrayList<>();

    public Board(DHBWGame game) {
        this.game = game;
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

    public DHBWGame getGame() {
        return game;
    }
}
