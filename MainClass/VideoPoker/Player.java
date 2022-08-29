package VideoPoker;

import java.util.*;

/**
 * Represents an actual player, who can interact with the game.
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class Player{
    //Information of a player
    protected int credit;
    protected int initialCredit;
    protected int betValue;
    protected Hand hand;
    protected Hand save;
    protected ArrayList<Integer> holdCards;
    protected double[] handStatistics;

    protected double sumGains;

    /**
     * Constructor of a Player that receives as an argument the number of credits that player will
     * have at his disposal and the value he will bet
     * @param valueC value of the player initial credit
     * @param valueB value of the player bet
     */
    public Player(int valueC, int valueB){
        credit = valueC;
        initialCredit = valueC;
        betValue = valueB;
        hand = new Hand();
        save = new Hand();
        holdCards = new ArrayList<>();
        handStatistics = new double[11];
        sumGains = 0;
    }

    /**
     * Takes credit from the player
     * @param value credit lost
     */
    public void loseCredit(int value) {
        credit -= value;
    }

    /**
     * Adds credit to the player money
     * @param value credit player won
     */
    public void winCredit(int value) {
        this.credit += value;
        sumGains += value;
     }
}

