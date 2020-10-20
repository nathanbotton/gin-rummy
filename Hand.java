/* -------------------------------------------------------------------------------------------------------------------*/
/* Hand.java                                                                                                          */
/* Author: Nathan Botton                                                                                              */
/* Description: Implement a Hand of Cards in Gin and its relevant operations.                                         */
/* -------------------------------------------------------------------------------------------------------------------*/

import java.util.Arrays;
import java.util.Stack;

public class Hand {
    private Card[] hand;                        // cards in the hand
    private static final int HAND_SIZE = 11;    // size of hand (capacity of 11), blank spot represented by 00
    private SetState champ;                     // best possible state


    // create hand (player goes first)
    public Hand(Deck deck, boolean player) {
        // insert 10 shuffled cards
        hand = new Card[HAND_SIZE];
        for (int i = 0; i < HAND_SIZE - 1; i++) {
            hand[i] = deck.deal();
        }
        if (player) hand[HAND_SIZE - 1] = deck.deal();      // give player an 11th card to start
        else hand[HAND_SIZE - 1] = new Card("0", "0");      // fill opponent's blank spot with card 00
        setChamp();                                         // set the current champion state
    }


    /* --------------------------------------------------------------------------------------------------------------*/
    /* ---------------------------------------SETSTATE NESTED CLASS--------------------------------------------------*/
    /* --------------------------------------------------------------------------------------------------------------*/

    // a SetState is a representation of the possible arrangements of sets a hand can take, and its cards' properties
    public static class SetState {
        private int num2 = 0;           // potential sets
        private int num3 = 0;           // sets of 3
        private int num4 = 0;           // sets of 4
        private Card[][] set3 = new Card[3][3];
        private Card[][] set4 = new Card[1][4];
        private Card[][] p2 = new Card[11][2];

        public SetState() {
        }

        public SetState(int num2, int num3, int num4) {
            this.num2 = num2;
            this.num3 = num3;
            this.num4 = num4;
        }

        public int firstEmpty(int n) {
            if (n == 2) {
                for (int i = 0; i < p2.length; i++) {
                    if (p2[i][0] == null) return i;
                }
            } else if (n == 3) {
                for (int i = 0; i < set3.length; i++) {
                    if (set3[i][0] == null) return i;
                }
            } else if (n == 4) {
                for (int i = 0; i < set4.length; i++) {
                    if (set4[i][0] == null) return i;
                }
            }

            return -1;
        }

        // make a card's status part of a set
        public void addSet(Stack<Card> s, int len) {
            int spot = firstEmpty(len);

            if (len == 2) {
                while (!s.isEmpty()) {
                    Card c = s.pop();
                    p2[spot][len - 1] = c;
                    len--;
                    c.putInPair(true);
                }
                num2++;
            } else if (len == 3) {
                while (!s.isEmpty()) {
                    Card c = s.pop();
                    set3[spot][len - 1] = c;
                    len--;
                    c.putInSet(true);
                    if (c.getInPair()) removePair(c);
                }
                num3++;
                // System.out.println("--3add");
            } else if (len == 4) {
                while (!s.isEmpty()) {
                    Card c = s.pop();
                    set4[spot][len - 1] = c;
                    len--;
                    c.putInSet(true);
                    if (c.getInPair()) removePair(c);
                }
                num4++;

            }
        }

        // remove pair if one of them becomes part of a set
        public void removePair(Card c) {
            boolean contained;
            for (int i = 0; i < p2.length; i++) {
                contained = false;
                for (int j = 0; j < p2[0].length; j++) {
                    if (p2[i][j] != null && p2[i][j].equals(c)) {
                        for (Card card : p2[i]) card.putInPair(false);
                        contained = true;
                    }
                }
                if (contained) {
                    p2[i][0] = null;
                    p2[i][1] = null;
                    num2--;
                }


            }
        }


        // return the better of the two states
        public SetState champion(SetState that) {
            if ((3 * this.num4 + 2 * this.num3) > (3 * that.num4 + 2 * that.num3))
                return this;
            else if ((3 * this.num4 + 2 * this.num3) < (3 * that.num4 + 2 * that.num3))
                return that;
            else if (this.num2 > that.num2)
                return this;
            else return that;
        }

        // string representation of state
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("\t").append(num3).append(" three-set(s): ");
            for (Card[] cards : set3) {
                if (cards[0] != null) {
                    str.append("[");
                    for (int i = 0; i < cards.length; i++) {
                        str.append(cards[i]);
                        if (i != cards.length - 1) str.append(" ");
                    }
                    str.append("]  ");
                }
            }
            str.append("\n\t");

            str.append(num4).append(" four-set(s): ");
            for (Card[] cards : set4) {
                if (cards[0] != null) {
                    str.append("[");
                    for (int i = 0; i < cards.length; i++) {
                        str.append(cards[i]);
                        if (i != cards.length - 1) str.append(" ");

                    }
                    str.append("]  ");
                }
            }
            str.append("\n\t");

            str.append(num2).append(" pair(s): ");
            for (Card[] cards : p2) {
                if (cards[0] != null) {
                    str.append("(");
                    for (int i = 0; i < cards.length; i++) {
                        str.append(cards[i]);
                        if (i != cards.length - 1) str.append(" ");
                    }
                    str.append(")  ");
                }
            }
            str.append("\n");

            return str.toString();
        }
    }


    /* ---------------------------------------------------------------------------------------------------------------*/
    /* ---------------------------------------------HAND OPERATIONS---------------------------------------------------*/
    /* ---------------------------------------------------------------------------------------------------------------*/

    // set the current champion state and update the cards' statuses
    public void setChamp() {
        champ = checkOrder().champion(checkDup());      // return the better of the two states
    }

    // add card to hand and update champion SetState
    public void add(Card c) {
        hand[0] = c;
        setChamp();
    }

    // dump a card from hand, update champion SetState
    public Card dump(int i) {
        Card c = hand[i];
        hand[i] = hand[HAND_SIZE - 1];
        hand[HAND_SIZE - 1] = new Card("0", "0");
        setChamp();
        return c;
    }

    // judge a card (for opponent) -- add it, see if it attains in better SetState, and keep it if it does
    public boolean judge(Card c) {
        SetState old = new SetState(champ.num2, champ.num3, champ.num4);    // current SetState, for comparison
        add(c);
        if (champ.champion(old) == champ) return true;          // if SetState is better, keep the card and return true
        else {                                                  // otherwise, dump the card and return false
            for (int i = 0; i < HAND_SIZE; i++) {
                if (hand[i].equals(c)) {
                    dump(i);
                    break;
                }
            }
            return false;
        }
    }

    // strategically dump a card in the opponent's hand -- primarily if it's not in a set and doesn't have potential
    // and otherwise if it's not in a set
    public Card oppDump() {
        int spot = -1;
        Card c;
        for (int i = 0; i < HAND_SIZE; i++) {               // find card not in set and without potential
            if (hand[i].getValue() == 0) continue;
            if (!hand[i].getInSet()) {
                if (!hand[i].getInPair()) {
                    spot = i;
                    break;
                }
            }
        }

        if (spot != -1) c = dump(spot);                     // dump the card if found
        else {                                              // otherwise, dump the first card not in a set
            for (int i = 0; i < HAND_SIZE; i++) {
                if (!hand[i].getInSet()) {
                    spot = i;
                    break;
                }
            }
            c = dump(spot);
        }
        return c;
    }

    // check if the given SetState is a winning one
    public Boolean win() {
        return champ.num3 == 2 && champ.num4 == 1;
    }

    // return the current champion state
    public SetState getChamp() {
        return champ;
    }

    // return the current hand
    public Card[] getHand() {
        return hand;
    }

    // clear the statuses of the cards in the hand
    public void clear() {
        for (Card c : hand) {
            if (c != null) {
                c.putInSet(false);
                c.putInPair(false);
            }
        }
    }


    /* ---------------------------------------------------------------------------------------------------------------*/
    /* ---------------------------------------FIND SETS WITHIN HAND---------------------------------------------------*/
    /* ---------------------------------------------------------------------------------------------------------------*/

    // return the state given by checking primarily for ordered sets
    public SetState checkOrder() {
        clear();                                    // clear statuses of cards
        SetState set = new SetState();              // initialize new state
        findOrderedSet(set);                        // update state for ordered sets
        findDupSet(set);                            // update state for duplicate sets
        return set;                                 // return the SetState
    }

    // return the state given by checking primarily for duplicates sets
    public SetState checkDup() {
        clear();                                    // clear statuses of cards
        SetState set = new SetState();              // initialize new state
        findDupSet(set);                            // update state for duplicate sets
        findOrderedSet(set);                        // update state for ordered sets
        return set;                                 // return the SetState
    }


    // find sets of same values
    public void findDupSet(SetState s) {
        Arrays.sort(hand);                                          // sort hand by value

        for (int i = 0; i < HAND_SIZE; i++) {
            // orderFirst &&
            if (hand[i].getInSet()) continue;        // ensure card isn't already in a set

            // create queue to store cards in potential set, push current card
            Stack<Card> dups = new Stack<Card>();
            dups.push(hand[i]);
            int inStack = 1;

            // while set conditions met, add next card to set
            // conditions: don't pass last card, values are the same, stack doesn't exceed 4 cards
            // (or 3 if there's already a 4 set)
            while (i < HAND_SIZE - 1 && hand[i + 1].getValue() == hand[i].getValue() && (inStack < 3 ||
                    (s.num4 == 0 && inStack < 4))) {
                // ensure next card isn't already in a set
                if (hand[i + 1].getInSet()) {
                    i++;
                    continue;
                }
                dups.push(hand[i + 1]);
                inStack++;
                i++;
            }

            if (inStack == 2 || inStack == 3 || inStack == 4) s.addSet(dups, inStack);
        }
    }


    // find sets of ordered values
    private void findOrderedSet(SetState s) {
        Arrays.sort(hand, new Card.bySuit());                   // sort hand by suit

        for (int i = 0; i < HAND_SIZE; i++) {
            //dupFirst &&
            if (hand[i].getInSet()) continue;      // ensure card isn't already in a set
            int value = hand[i].getValue();
            String suit = hand[i].getSuit();

            // create stack to store cards in potential set, push current card
            Stack<Card> ords = new Stack<Card>();
            ords.push(hand[i]);
            int inStack = 1;
            int j = 1;

            // while set conditions met, add next card to set
            // conditions: don't pass last card, suit is same, value is correct increment, stack doesn't exceed 4 cards
            // (or 3 if there's already a 4 set)
            while (i < HAND_SIZE - 1 && hand[i + 1].getSuit().equals(suit) && hand[i + 1].getValue() == value + j &&
                    (inStack < 3 || (s.num4 == 0 && inStack < 4))) {
                // ensure next card isn't already in A set
                if (hand[i + 1].getInSet()) {
                    i++;
                    continue;
                }
                ords.push(hand[i + 1]);
                inStack++;
                j++;
                i++;
            }
            if (inStack == 2 || inStack == 3 || inStack == 4) s.addSet(ords, inStack);
        }
    }



    /* ---------------------------------------------------------------------------------------------------------------*/
    /* -----------------------------------------TOSTRING REPRESENTATION-----------------------------------------------*/
    /* ---------------------------------------------------------------------------------------------------------------*/

    // toString method-- print in value order
    public String toString() {
        Arrays.sort(hand);
        StringBuilder str = new StringBuilder();
        for (Card c : hand) {
            if (!c.toString().equals("00")) {
                str.append(c.picRep()).append(" ");
            }
        }
        // str.append("\n").append(champ);

        return str.toString();
    }


}
