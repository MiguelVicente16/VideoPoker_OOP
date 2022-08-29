package VideoPoker;

import java.util.*;

/**
 * Represents a hand of a player, with five cards and all the actions associated to it
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class Hand{

    protected List<Card> cards;

    /**
     * Constructor that creates a new and empty hand
     */
    public Hand(){
        this.cards = new ArrayList<>();
    }

    /**
     * Clean/empty the hand
     */
    public void Clean(){
        this.cards.clear();
    }

    /**
     * Add a card to the player hand
     * @param card card to add
     */
    public void Add(Card card){
        this.cards.add(card);
    }

    /**
     * Add a cards in a certain position of the player hand
     * @param i position to add the card
     * @param card card to add
     */
    public void Add(int i, Card card){
        this.cards.add(i,card);
    }

    /**
     * Add all cards of a List to the player hand
     * @param card List cards to add
     */
    public void AddAll(List<Card> card){
        this.cards.addAll(card);
    }

    /**
     * Get a card from a certain position of the player hand
     * @param i position to add the card
     * @return Card
     */
    public Card Get(int i) {
        return cards.get(i);
    }

    /**
     * Remove a card from a certain position of the player hand
     * @param i position to add the card
     */
    public void Remove(int i){
        this.cards.remove(i);
    }

    /**
     * Remove and insert a card in a certain position of the player hand
     * @param i position to add the card
     * @param card card to insert
     */
    public void Set(int i, Card card){
        this.Remove(i);
        this.Add(i, card);
    }

    /**
     * Method to tell the size of the deck
     * @return int size of deck
     */
    public int size(){
        return this.cards.size();
    }

    /**
     * Sort the card by ranks
     */
    public void sortHand() {
        Card aux;

        try {
            for (int i = 0; i < 4; i++) {
                int idx = i;
                for (int j = i + 1; j < 5; j++) {

                    if (cards.get(j).compareTo(cards.get(idx)) < 0) {
                        idx = j;
                    }
                }
                aux = cards.get(idx);
                cards.remove(idx);
                cards.add(idx, cards.get(i));
                cards.remove(i);
                cards.add(i, aux);
            }
        } catch (NullPointerException e){
            System.out.println("Error: Pointer " + e.getMessage());
        }
    }

    /**
     * Overrides the toString() method and transform the List of type Card to type String
     * @return String list of cards
     */
    public String toString(){//overriding the toString() method
        return cards.toString();
    }

}
