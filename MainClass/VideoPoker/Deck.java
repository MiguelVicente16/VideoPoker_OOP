package VideoPoker;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent a deck of 52 cards
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class Deck {
    //Array keeping the playable cards during each game;
    protected ArrayList<Card> playDeck;
    // Array keep track of the original 52 cards
    protected final ArrayList<Card> saveDeck;

    /**
     * Constructor that will build  one deck of cards (used in simulation mode)
     */
    public Deck() {
        List<String> suits = Card.getValidSuit();
        List<String> ranks = Card.getValidRank();

        playDeck = new ArrayList<>();
        saveDeck = new ArrayList<>();

        try {
            for (String suit : suits) {
                for (String rank : ranks) {
                    playDeck.add(new Card(rank, suit));
                    saveDeck.add(new Card(rank, suit));
                }
            }

        } catch (PlayingCardException e) {
            e.printStackTrace();
            System.out.println("PlayingCardException: " + e.getMessage());
        }
    }

    /**
     * Constructor that will be used on DebugMode to create a Deck with cards read from input file
     * @param cardFile - String with file's name
     * @throws PlayingCardException preventing from an exception in file reader
     */
    public Deck(String cardFile) throws PlayingCardException{
        int card = -1;
        int iterator;
        char rank = '\0';

        playDeck = new ArrayList<>();
        saveDeck = new ArrayList<>();

        try {
            FileReader cardsFile = new FileReader(cardFile);

            while ((iterator = cardsFile.read()) != -1) {
                if (((char) iterator != ' ') && ((char) iterator != '\n') && ((char) iterator != '\t') && ((char) iterator != '\r') && ((char) iterator != '\f')) {
                    if (card == -1) {
                        card = 1;
                        rank = (char) iterator;
                    } else {
                        String str1 = String.format("%s", rank);
                        String str2 = String.format("%s", (char) iterator);
                        playDeck.add(new Card(str1, str2));
                        saveDeck.add(new Card(str1, str2));
                        card = -1;
                    }
                }
            }
            cardsFile.close();
        } catch (IOException e){
            throw new PlayingCardException("Error: " +e.getMessage());
        }
    }



    /**
     * Shuffles cards in playDeck using collections framework
     *
     */
    public void shuffle(){
        Collections.shuffle(playDeck);
    }

    /**
     * Method that deal one card from the deal deck
     * @return number of cards dealt
     */

    public Card dealOneCard() {

        Card dealtCard;

        if (1 > remain()) {
            System.out.println("The deck doesn't have that many cards");
            System.exit(-1);
        }
        dealtCard = removeCard();

        return dealtCard;
    }

    /**
     * Method to deal 5 cards to the player hand
     * @param player - The actual player (object player of class Player)
     */
    public void deal5Cards(Player player){
        for (int j = 0; j < 5; j++) {
            player.hand.Add(dealOneCard());
        }
        player.save.AddAll(player.hand.cards);
    }

    /**
     * Reset playDeck by copying all cards from saveDeck
     */
    public void reset(){
        playDeck.clear();
        playDeck.addAll(saveDeck);
    }

    /**
     * Method to remove the first Card from the Deck
     * @return the Card removed
     */
    public Card removeCard(){
        return playDeck.remove(0);

    }

    /**
     * Check the size of playDeck and return its value
     * @return size of  playDeck
     */
    public int remain(){
        return playDeck.size();
    }

    /**
     * Deal new cards to the position which is not to hold
     * @param player - The actual player (object player of class Player)
     */
    public void getNewPlayerHand(Player player){
        player.hand.Clean();
        player.hand.AddAll(player.save.cards);
        for (int j = 0; j < 5; j++) {
            if (!player.holdCards.contains(j + 1)) {
                Card newCard = dealOneCard();
                player.hand.Set(j, newCard);
                player.save.Set(j, newCard);
            }
        }
    }

}
