package VideoPoker;

import java.util.Arrays;
import java.util.List;

/**
 * Represent a playing card objets.It contains its suit, its rank
 * and all suits and ranks that a card might have.
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class Card implements Comparable<Card>{
    //Constant vector with suits
    static final String[] Suit = {"C","D","H","S"};
    //Constant vector with ranks
    static final String[] Rank = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};

    //Information of a card
    private String cardRank;
    private String cardSuit;
    private int cardRankValue;
    private int cardSuitValue;

    /**
     * Constructor to create a card
     * @param rank rank of a card
     * @param suit suit of a card
     * @throws PlayingCardException if rank or suit are invalid
     */
    public Card(String rank, String suit) throws PlayingCardException {
        setCardRank(rank);
        setCardSuit(suit);
    }

    /**
     * Getter of card rank
     * @return String card rank
     */
    public String getCardRank() {
        return cardRank;
    }

    /**
     * Getter of card rank value
     * @return int rank value
     */
    public int getCardRankValue() {
        return cardRankValue;
    }

    /**
     * Getter of card suit
     * @return String card suit
     */
    public String getCardSuit() {
        return cardSuit;
    }

    /**
     * Getter of card suit value
     * @return int suit value
     */
    public int getCardSuitValue() {
        return cardSuitValue;
    }

    /**
     * Method that returns a list of the valid ranks
     * @return List Rank
     */
    public static List<String> getValidRank(){
        return Arrays.asList(Rank);
    }

    /**
    /**
     * Method that returns a list of the valid suits
     * @return List Suit
     */
    public static List<String> getValidSuit(){
        return Arrays.asList(Suit);
    }

    /**
     * This method(Setter) validates the rank and set the instance variable
     * also set a value to the rank in crescent order beginning in 2 and following the rank array index
     * @param cardRank rank of a card
     * @throws PlayingCardException if a card rank is invalid
     */
    public void setCardRank(String cardRank) throws PlayingCardException {
        List<String> validRanks = getValidRank();
        cardRank = cardRank.toUpperCase();

        if(validRanks.contains(cardRank)) {
            this.cardRank = cardRank;
            this.cardRankValue = validRanks.indexOf(cardRank)+2;
        } else{
            System.out.println(cardRank);
            throw new PlayingCardException("Invalid rank: " + cardRank);
        }
    }

    /**
     * This method(Setter) validates the suit and set the instance variable
     * also set a value to the suit in crescent order of suit array index
     * @param cardSuit suit of a card
     * @throws PlayingCardException if a suit rank is invalid
     */
    public void setCardSuit(String cardSuit) throws PlayingCardException {
        List<String> validSuits = getValidSuit();
        cardSuit = cardSuit.toUpperCase();

        if(validSuits.contains(cardSuit)) {
            this.cardSuit = cardSuit;
            this.cardSuitValue = validSuits.indexOf(cardSuit);
        } else{
            System.out.println(cardSuit);
            throw new PlayingCardException("Invalid suit: "+cardSuit);
        }
    }

    /**
     * Compare which rank of which card is higher
     * @param card the object to be compared.
     * @return int
     */
    @Override
    public int compareTo(Card card) {
        return Integer.compare(cardRankValue, card.cardRankValue);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (this.cardRankValue != other.cardRankValue)
            return false;
        if (this.cardSuitValue != other.cardSuitValue)
            return false;
        return true;
    }

    /**
     * Creates a string(card) with both the rank and the suit
     * @return String a card with a rank and a suit
     */
    public String toString(){
        return String.format("%s%s", cardRank, cardSuit);
    }


}
