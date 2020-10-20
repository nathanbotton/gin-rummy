/* -------------------------------------------------------------------------------------------------------------------*/
/* PlayGin.java                                                                                                       */
/* Author: Nathan Botton                                                                                              */
/* Description: Simulate game of Gin against strategic opponent using player Hands and a deck of Cards                */
/* -------------------------------------------------------------------------------------------------------------------*/

import java.util.Scanner;

public class PlayGin {
    private static final int HAND_SIZE = 11;                    // hand size
    private static Deck deck = new Deck();                      // deck for game to be played with
    private static Hand player = new Hand(deck, true);          // player's hand
    private static Hand opponent = new Hand(deck, false);       // opponent's hand
    private static Card down = null;                            // down card
    private static boolean turn = false;                        // turn signal
    private static boolean exhausted = false;                   // signal no cards left
    private static int round = 1;                               // round count
    private static Scanner in = new Scanner(System.in);         // scanner
    // game settings
    private static boolean seeOpponent = false;                 // let player see opponent's hand
    private static boolean handVisualization = false;           // display hand in organized manner
    private static boolean memory = false;                      // allow opponent to remember player pickups
    private static Card[] inPlayerHand = new Card[HAND_SIZE];   // allow opponent to track player's hand
    private static int trackPlayerHand = 0;                     // track amount of cards opponent remembers


    // simulate game
    public static void main(String[] args) {

        printSettingsAndInstructions();

        // start the game with player going first
        playRoundOne();

        // while there is no winner and there are still cards in the deck, play rounds
        while (!player.win() && !opponent.win() && !exhausted) {
            printRoundCount();

            if (turn) playPlayer();
            else playOpponent();

            round++;
        }

        endGame();
    }

    /* ---------------------------------------------------------------------------------------------------------------*/
    /* ------------------------------------------PLAYING OPERATIONS---------------------------------------------------*/
    /* ---------------------------------------------------------------------------------------------------------------*/

    // start game with player's turn
    public static void playRoundOne() {
        printRoundCount();
        round++;
        // show player their hand
        System.out.println("Your hand: " + player);
        if (handVisualization) System.out.println(player.getChamp());
        System.out.println();
        System.out.println("You go first!");
        System.out.println();
        // prompt player to dump a card, make sure input is valid card to dump
        promptDump();
        System.out.println();
    }

    // play player's turn
    public static void playPlayer() {
        System.out.println("Your turn!");
        System.out.println("Your hand: " + player);
        if (handVisualization) System.out.println(player.getChamp());
        System.out.println();

        promptPickup();
        promptDump();
        turn = !turn;

        System.out.println();

    }

    // prompt player to pick up card
    public static void promptPickup() {
        System.out.println("Down card: " + down.picRep());
        System.out.println("Pick up down card (y) or pick from the deck (n) ? ");
        String response = in.nextLine();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Not appropriate response. Try again.");
            response = in.nextLine();
        }
        System.out.println();

        if (response.equals("y")) {
            player.add(down);
            if (memory) {
                inPlayerHand[trackPlayerHand] = down;
                trackPlayerHand++;
            }
            System.out.println("Your hand: " + player);
            if (handVisualization) System.out.println(player.getChamp());
        } else {
            Card c = deck.deal();
            if (c == null) {
                exhausted = true;
                return;
            }
            player.add(c);
            System.out.println("You picked " + c.picRep());
            System.out.println("Your hand: " + player);
            if (handVisualization) System.out.println(player.getChamp());
        }
        System.out.println();
    }

    // prompt player to dump card
    public static void promptDump() {
        int spot = -1;                          // to ensure card is valid
        Card[] hand = player.getHand();
        while (spot == -1) {
            System.out.println("Choose a card to dump: ");
            String card = in.nextLine();

            for (int i = 0; i < HAND_SIZE; i++) {
                if (card.equals(hand[i].toString())) spot = i;
            }

            if (spot == -1) System.out.println("Card not in hand. Try again.");
            else {
                down = player.dump(spot);
                if (memory) {
                    // remove from inPlayerHand if it's in it
                    for (int i = 0; i < HAND_SIZE; i++) {
                        if (inPlayerHand[i] != null && inPlayerHand[i].equals(down)) {
                            inPlayerHand[i] = inPlayerHand[trackPlayerHand - 1];
                            inPlayerHand[trackPlayerHand - 1] = null;
                            trackPlayerHand--;
                            break;
                        }
                    }
                }
            }
        }
    }

    // play opponent's turn
    public static void playOpponent() {
        System.out.println("Opponent's turn.");
        if (seeOpponent) {
            System.out.println("Opponent's hand: " + opponent);
            if (handVisualization) System.out.println(opponent.getChamp());
        }
        System.out.println();

        // opponent add
        if (opponent.opponentAdd(down)) {
            System.out.println("Opponent picked up your " + down.picRep() + ".");
        } else {
            System.out.println("Opponent picked from deck.");
            Card c = deck.deal();
            if (c == null) {
                exhausted = true;
                return;
            }
            opponent.add(c);
        }
        System.out.println();

        // opponent dump
        down = opponent.opponentDump(inPlayerHand, memory);
        System.out.println("Opponent dumped " + down.picRep() + ".");
        if (seeOpponent) {
            System.out.println("Opponent's hand: " + opponent);
            if (handVisualization) System.out.println(opponent.getChamp());
        }
        System.out.println();
        
        turn = !turn;
    }

    /* ---------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------PRINTING METHODS---------------------------------------------------*/
    /* ---------------------------------------------------------------------------------------------------------------*/

    // print settings and instructions
    public static void printSettingsAndInstructions() {
        // HandVisualization
        System.out.println();
        System.out.println("Would you like to play with HandVisualization? This will help you view your hand in an " +
                "assistive layout (recommended). Type \"y\" if so, and any other key otherwise:");
        if (in.nextLine().equals("y")) handVisualization = true;

        // OpponentMemory
        System.out.println();
        System.out.println("Would you like to play with OpponentMemory? This will allow your opponent to remember " +
                "which cards you pick up and put down and play accordingly. This will increase the difficulty of the " +
                "game. Type \"y\" if so, and any other key otherwise:");
        if (in.nextLine().equals("y")) memory = true;
        System.out.println();

        //SeeOpponent
        System.out.println("Would you like to see your opponent's hand throughout the game? This is if you want to " +
                "see how the computer strategizes and plays (not recommended for regular playing). Type \"y\" if so, " +
                "and any other key otherwise:");
        if (in.nextLine().equals("y")) seeOpponent = true;
        System.out.println();

        // Instructions
        System.out.println("Instructions: when typing in a card to dump, type its rank (number or capital letter) " +
                "followed by the first letter of its suit in lowercase (refer to the 3 of diamonds as \"3d\" and the " +
                "King of hearts as \"Kh\")");
        System.out.println();
        System.out.println("Ready? Click enter to play.");
        String r = in.nextLine();
        System.out.println("Good luck!");
        System.out.println();
    }

    // print the current round
    public static void printRoundCount() {
        System.out.println("--------------------- ROUND " + round + " ------------------------");
        System.out.println();
    }

    // end the game, show results
    public static void endGame() {
        System.out.println("============================ GAME OVER ============================");
        System.out.println();

        // show results
        if (player.win()) System.out.println("You win!");
        else if (opponent.win()) System.out.println("You lose.");
        else System.out.println("Deck has run out, no winner.");
        System.out.println();

        // show hands
        System.out.println("Your hand: " + player);
        System.out.println(player.getChamp());
        System.out.println();
        System.out.println("Opponent's hand: " + opponent);
        System.out.println(opponent.getChamp());
        System.out.println();

        System.out.println("Thanks for playing!");
        System.out.println("Game lasted " + (round - 1) + " rounds.");
        System.out.println("==================================================================");
        System.out.println();
    }
}

