/* -------------------------------------------------------------------------------------------------------------------*/
/* Card.java                                                                                                          */
/* Author: Nathan Botton                                                                                              */
/* Description: Implement a Card data type to be used in a game of Gin.                                               */
/* -------------------------------------------------------------------------------------------------------------------*/

import java.util.Comparator;

public class Card implements Comparable<Card> {
    private int value;              // numeric value of card's rank
    private String rank;            // card's rank
    private String suit;            // card's suit
    private boolean inSet;          // denotes if card is part of a pair in the player's hand
    private boolean inPair;         // denotes if card is part of a pair in the player's hand

    // Card constructor
    public Card(String rank, String suit) {
        this.suit = suit;
        this.rank = rank;
        inSet = false;

        // assign value by rank
        switch (rank) {
            case "A":
                value = 1;
                break;
            case "J":
                value = 11;
                break;
            case "Q":
                value = 12;
                break;
            case "K":
                value = 13;
                break;
            default:
                value = Integer.parseInt(rank);
                break;
        }
    }

    // return value
    public int getValue() {
        return value;
    }

    // return suit
    public String getSuit() {
        return suit;
    }

    // return if the card is in a set in a player's hand
    public Boolean getInSet() {
        return inSet;
    }

    // set inSet (put card in or take card out of set)
    public void putInSet(boolean in) {
        inSet = in;
    }

    // return if the card is in a pair in a player's hand
    public Boolean getInPair() {
        return inPair;
    }

    // set inSet (put card in or take card out of pair)
    public void putInPair(boolean in) {
        inPair = in;
    }

    // returns if this card can pair with another card
    public boolean canPairWith(Card that) {
        if (that == null) return false;
        if (this.value == that.value) return true;
        return this.suit.equals(that.suit) && (this.value + 1 == that.value || this.value - 1 == that.value);
    }

    // returns if this card can pair with any card in an array of cards
    public boolean canPairWithAny(Card[] cards) {
        for (Card c : cards) if (this.canPairWith(c)) return true;
        return false;
    }

    // compare cards by suit (then by rank)
    public static class bySuit implements Comparator<Card> {
        public int compare(Card a, Card b) {
            if (!a.suit.equals(b.suit)) {
                return a.suit.compareTo(b.suit);
            } else return a.value - b.value;
        }
    }

    // compare cards by value (then by suit)
    public int compareTo(Card that) {
        if (that == null) return -1;
        if (this.value != that.value) {
            return this.value - that.value;
        } else return this.suit.compareTo(that.suit);
    }

    // picture representation of card
    public String picRep() {
        String str = rank;
        switch (suit) {
            case "h":
                str += "\u2665";
                break;
            case "s":
                str += "\u2660";
                break;
            case "c":
                str += "\u2663";
                break;
            case "d":
                str += "\u2666";
                break;
        }
        return str;
    }

    // toString method
    public String toString() {
        return rank + suit;
    }

}

