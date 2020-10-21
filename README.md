# gin-rummy
Play Gin against a strategic opponent.          
Author: Nathan Botton
-----------------------------------------------

<strong>How to play:</strong><br/> 
The objective of the game is to collect three sets of cards (two sets of three cards and one set of four cards) before your opponent does.<br/>
A set of cards can be formed from a group of cards (three or four cards with the same rank) or a sequence of cards (three or four cards with incrementing ranks and the same suit - note that sequences cannot run past the end of the deck, so a King cannot combine with an Ace to form a sequence).<br/>
Every turn, the player whose turn it is will choose to pick up the card from the down pile or take an unknown card from the deck, and then must dump a card to the down pile, ending their turn with 10 cards in their hand.<br/>
The first player will start with 11 cards (and the opponent with 10) and initially chooses a card to dump, starting the down pile.<br/> 
The first player to obtain two three-sets and one four-set wins the game.<br/><br/>

<strong>The Code:</strong><br/>
The game is played with <strong>Card</strong>s:

    -Each Card has a rank, a suit, and a numerical value associated with its rank (1 for A, 11 for J, 12 for Q, 13 for K).
    -Each Card also has the quality of being included in a set in either player's hand, or being included in a pair in either player's hand.
    -Cards can be compared and ordered by value or by suit primarily.
    -Each Card also has the quality of pairing with other Cards (if their values are the same or if their suits are the same and their values differ by 1).

Each game is played with a <strong>Deck</strong> of Cards:

    -A Deck is a shuffled array of 52 unique Cards.
    -A Deck can be dealt from, and the dealt Card will not be dealt again.

At the heart of the game are the <strong>Hand</strong>s (the cards in the hand of the player and the opponent):

    -Each Hand starts with 10 Cards dealt from the shuffled Deck, and the player is dealt one more Card to begin.
    -For the player:
        -A Card can be added to the Hand.
        -A Card can be dumped from the Hand.
    -For the opponent:
        -A Card can be strategically added to the Hand (the opponent will check if the Card makes his Hand better than it previously was).
        -A Card can be strategically dumped from the Hand (the opponent will dump its least valuable Card, one that doesn't contribute to a set and one that isn't            in a pair if there are any).
    -The Hand can be checked to see if it is a winning hand (contains two 3-sets and one 4-set).
    -The Hand can be checked for sets of Cards in multiple ways and return the best arrangement of sets.
        -Sets can be searched for primarily by groups (duplicate values) and then by sequences (ordered values with the same suit) with the remaining Cards, OR               primarily by sequences and then by groups with the remaining Cards. If the Hand is in a winning state, one of these will detect it.

Within the Hand is a <strong>SetState</strong>:

    -A SetState is a possible arrangement of sets the Hand contains.
    -As the Hand is being searched for sets, found sets are added to the SetState.
    -The SetState contains sets/pairs of Cards in arrays of sets (which are represented as arrays of Cards).
    -A SetState can be checked to see if it is in a winning state (contains two 3-sets and one 4-set) -- this is used to check if a Hand is a winning hand.
    -SetStates can be relatively compared to show the player the best arrangement of Cards they have, and a winning SetState will always win in a comparison.

The game is played through <strong>PlayGin</strong>:

    -The game's instructions are printed, and the player can choose certain settings of the game.
    -The game begins, and while there is no winner, rounds are played.
    -The player is prompted to pick up a card and then dump a card during their turn, and the opponent's round is automated.
    -Once there is a winner or the deck of cards is exhausted, the game ends and the results are shown.




