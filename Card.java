/* -------------------------------------------------------------------------------------------------------------------*/
/* Card.java                                                                                                          */
/* Author: Nathan Botton                                                                                              */
/* Description: Implement a Card data type to be used in a game of Gin.                                               */
/* -------------------------------------------------------------------------------------------------------------------*/

import java.util.Comparator;

public class Card implements Comparable<Card> {
    private int value;
    private String rank;
    private String suit;
    private boolean inSet;
    private boolean inPair;

    public Card(String rank, String suit) {
        this.suit = suit;
        this.rank = rank;
        inSet = false;

        // assign value
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

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public Boolean getInSet() {
        return inSet;
    }

    public void putInSet(boolean in) {
        inSet = in;
    }

    public Boolean getInPair() {
        return inPair;
    }

    public void putInPair(boolean in) {
        inPair = in;
    }


    // compare by suit
    public static class bySuit implements Comparator<Card> {
        public int compare(Card a, Card b) {
            if (!a.suit.equals(b.suit)) {
                return a.suit.compareTo(b.suit);
            } else return a.value - b.value;
        }
    }


    // comparator (by value first, then suit)
    public int compareTo(Card that) {
        if (that == null) return -1;
        if (this.value != that.value) {
            return this.value - that.value;
        } else return this.suit.compareTo(that.suit);
    }

    // suit picture representation of card
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

