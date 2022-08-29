package VideoPoker;

/**
 * Simulation mode is where the game is automatically played with a perfect strategy
 * in order to understand the average gain in the player’s credit.
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class SimulationMode {

    //Deck Object
    protected Deck deckOfCards;
    //Player object
    protected Player player;
    // Class that implements the Video Poker variant double bonus
    protected DoubleBonus doublebonus;


    /**
     * Constructor that initializes the variables and start a game with the perfect strategy
     * @param credit initial credit of the player
     * @param bet amount of credits the player will bet in every play
     * @param numDeals total number of deals in one game
     * @throws PlayingCardException if the bet value is valid
     */
    public SimulationMode(String credit, String bet, String numDeals) throws PlayingCardException {
        player = new Player(Integer.parseInt(credit),Integer.parseInt(bet));
        deckOfCards = new Deck();
        int nDeals = Integer.parseInt(numDeals);
        playPerfect(nDeals);
    }

    /**
     * Play the game with a perfect strategy numDeals times
     * @param numDeals times the game will me simulated, as in number of plays of the game
     * @throws PlayingCardException preventing from an exception in file reader and scan
     */
    public void playPerfect(int numDeals) throws PlayingCardException {

        int typeHand;

        if (player.betValue < 0 || player.betValue > 5) {
            throw new PlayingCardException("The bet can only be a integer between 1-5");
        }

        for (int i = 0; i < numDeals; i++) {

            player.loseCredit(player.betValue);

            deckOfCards.reset();
            deckOfCards.shuffle();

            player.hand.Clean();
            player.save.Clean();
            player.holdCards.clear();

            deckOfCards.deal5Cards(player);

            doublebonus = new DoubleBonus(player);
            doublebonus.advice();

            deckOfCards.getNewPlayerHand(player);

            doublebonus = new DoubleBonus(player);
            typeHand = doublebonus.TypeOfHand();

            if (typeHand != -1) {
                player.winCredit(doublebonus.multipliers[typeHand] * player.betValue);
            }
            if (player.credit <= 0){
                throw new PlayingCardException("The credit ended. Lose Game.");
            }

        }
        doublebonus = new DoubleBonus(player);
        doublebonus.statistics();
        double sumBets = player.betValue*numDeals;
        double balance = player.sumGains/sumBets*100;
        System.out.println("Credit\t\t\t\t"+player.credit + "(" + balance +"%)");

    }
}
