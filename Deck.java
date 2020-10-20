/* -------------------------------------------------------------------------------------------------------------------*/
/* Deck.java                                                                                                          */
/* Author: Nathan Botton                                                                                              */
/* Description: Implement a Deck of Cards to be used in a game of Gin.                                                */
/* -------------------------------------------------------------------------------------------------------------------*/

import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private Card[] deck;
    private int current = 0;
    private static final int NCARDS = 52;

    // Deck constructor: create and shuffle deck
    public Deck() {
        deck = new Card[NCARDS];
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"s", "c", "h", "d"};

        for (int i = 0; i < NCARDS; i++) {
            deck[i] = new Card(ranks[i % ranks.length], suits[i % suits.length]);
        }

        Collections.shuffle(Arrays.asList(deck));
    }

    // deal a card from the deck
    public Card deal() {
        if (current == NCARDS) return null;
        return deck[current++];
    }

    // toString method
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Card c : deck) {
            str.append(c).append(" ");
        }
        return str.toString();
    }

    // shuffle and print deck of cards and random (y/n) answers to simulate user input
    // for stress testing purposes
    public String testString() {
        Collections.shuffle(Arrays.asList(deck));
        StringBuilder str = new StringBuilder();
        for (Card c : deck) {
            str.append(c).append("\n");
        }
        double r = Math.random();
        if (r >= 0.5) str.append("y\n").append("n\n\n");
        else str.append("n\n").append("y\n\n");
        return str.toString();
    }

}
