# gin-rummy
Play gin against a strategic opponent.
Author: Nathan Botton

How to play gin:
The objective of the game is to collect three sets of cards (two sets of three cards and one set of four cards) before your opponent does.
A set of cards can be formed from a group of cards (three or four cards with the same rank) or a sequence of cards (three or four cards with incrementing ranks and the same suit - note that sequences cannot overlap, so a King cannot combine with an Ace to form a sequence).
The first player will start with 11 cards (and the opponent with 10) and initially chooses a card to dump, starting the down pile. Every turn, the player whose turn it is will choose to pick up the card from the down pile or take an unknown card from the deck, and then must dump a card to the down pile, ending their turn with 10 cards in their hand. 
The first player to obtain two three-sets and one four-set wins the game.

The code:
The game of gin is played with Cards: 
    Each card has a rank, a suit, and a numerical value associated with its rank (1 for A, 11 for J, 12 for Q, 13 for K).
    Each card also has the quality of being included in a set in either player's hand, or being included in a pair in either player's hand.
    Cards can be compared and ordered by value or by suit primarily.
    Each card also has the quality of pairing with other cards (if their values are the same or if their suits are the same and their values differ by 1).

Each game of gin is played with a Deck of Cards:
    A Deck is a shuffled array of 52 unique cards.
    A Deck can be dealt from, and the dealt card will be removed from the Deck.

At the heart of the game are the Hands (the cards in the hand of the player and the opponent):
    Each hand starts with 10 cards dealt from the shuffled Deck, and the player is dealt one more card to begin.
    For the player:
        A card can be added to the Hand.
        A card can be dumped from the Hand.
    For the opponent:
        A card can be strategically added to the Hand (the opponent will check if the card makes his Hand better than it previously was).
        A card can be strategically dumped from the Hand (the opponent will dump its least valuable card, one that doesn't contribute to a set and one that isn't in a pair if there are any).
    The Hand can be checked to see if it is a winning hand (contains two 3-sets and one 4-set).
    The Hand can be checked for sets of cards in multiple ways and return the best arrangement of sets.
        Sets can be searched for primarily by groups (duplicate values) and then by sequences (ordered values with the same suit) with the remaining cards, OR primarily by sequences and then by groups with the remaining cards. If the Hand is in a winning state, one of these will detect it.

Within the Hand is a SetState:
    A SetState is a possible arrangement of sets the hand contains.
    As the Hand is being searched for sets, found sets are added to the SetState.
    The SetState contains sets/pairs of cards in arrays of sets (which are represented as arrays of Cards).
    A SetState can be checked to see if it is in a winning state (contains two 3-sets and one 4-set) -- this is used to check if a Hand is a winning hand.
    SetStates can be relatively compared to show the player the best arrangement of cards they have, and a winning SetState will always win in a comparison.

The game is played through PlayGin
    The game's instructions are printed, and the player can choose certain settings of the game.
    The game begins, and while there is no winner, rounds are played.
    The player is prompted to pick up a card and then dump a card during their turn, and the opponent's round is automated.
    Once there is a winner or the deck of cards is exhausted, the game ends and the results are shown.


More detailed explanation of the code:
    ... to come


