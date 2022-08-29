package VideoPoker;

/**
 * Class that checks if the commands of the game are legal
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class Illegal {
    protected int bet;
    protected int betValue;
    protected int deal;
    protected int hold;

    /**
     * Constructor that defines if the commands are illegal (1) or legal (-1) at the beginning of the game
     */
    public Illegal(){
        bet = -1;
        betValue = -1;
        deal = 1;
        hold = 1;
    }

    /**
     * Defines that the Bet command is legal
     */
    public void LegalBet(){
        bet = -1;
    }

    /**
     * Define that the Bet Value is legal
     */
    public void LegalBetValue(){
        betValue = -1;
    }

    /**
     * Defines that the Deal command is legal
     */
    public void LegalDeal(){
        deal = -1;
    }

    /**
     * Defines that the Hold command is legal
     */
    public void LegalHold(){
        hold = -1;
    }

    /**
     * Defines that the Bet command is illegal
     */
    public void IllegalBet(){
        bet = 1;
    }

    /**
     * Defines that the Bet Value is illegal
     */
    public void IllegalBetValue(){
        betValue = 1;
    }

    /**
     * Defines that the Deal command is illegal
     */
    public void IllegalDeal(){
        deal = 1;
    }

    /**
     * Defines that the Hold command is illegal
     */
    public void IllegalHold(){
        hold = 1;
    }

    /**
     * Verify if the Bet command is legal
     * @return - true - if it's legal or - false - if it's illegal
     */
    public boolean IsLegalBet(){
        return bet == -1;
    }

    /**
     * Verify if the Bet Value is legal
     * @return - true - if it's legal or - false - if it's illegal
     */
    public boolean IsLegalBetValue(){
        return betValue == -1;
    }

    /**
     * Verify if the Deal command is legal
     * @return - true - if it's legal or - false - if it's illegal
     */
    public boolean IsLegalDeal(){
        return deal == -1;
    }

    /**
     * Verify if the Hold command is legal
     * @return - true -  if it's legal or - false - if it's illegal
     */
    public boolean IsLegalHold(){
        return hold == -1;
    }
}
