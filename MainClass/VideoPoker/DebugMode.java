package VideoPoker;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class that runs the debug mode, where the poker game is fully loaded from two files
 * and played according them
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class DebugMode{

    //auxiliary variable to count the credit the player bet
    private double betCount = 0;
    //auxiliary variable that checks if the input in the command file is numeric
    private int  intValue;
    //Player object
    protected Player player;
    //Deck Object
    protected Deck deckFile;
    // Class that implements the Video Poker variant double bonus
    protected DoubleBonus doublebonus;
    // Class that checks if the commands of the game are legal
    protected Illegal check;

    /**
     * Default constructor, open the files to read from and initialize player credits. All variables from cmd line.
     * @param Credit player initial credit
     * @param cmdFile File with the commands to play the game
     * @param cardFile File where we take the cards to play
     * @throws PlayingCardException preventing from an exception in file reader and scan
     */
    public DebugMode(String Credit, String cmdFile, String cardFile) throws PlayingCardException {

        List<String> commands = getCommands(cmdFile);

        deckFile = new Deck(cardFile);
        player = new Player(Integer.parseInt(Credit),5);

        checkMode(commands);

    }

    /**
     * Method that read and take the commands from the file im cmd line so
     * the debug mode can be played properly
     * @param cmdFile File with the debug mode commands
     * @return List A list with all the commands that will be played
     * @throws PlayingCardException preventing from an exception in file reader and scan
     */
    public List<String> getCommands(String cmdFile) throws PlayingCardException{
        try {
            FileReader commandsFile = new FileReader(cmdFile);
            Scanner scanCmd = new Scanner(commandsFile);
            List<String> commands = new ArrayList<>();

            while (scanCmd.hasNext()) {
                commands.add(scanCmd.next());
            }

            commandsFile.close();
            scanCmd.close();

            return commands;

        } catch (IOException e){
        throw new PlayingCardException("Error: " +e.getMessage());
        }
    }

    /**
     * Method to check which mode of the game the file request us to play.
     * @param playerCommand File with the debug mode commands
     */
    public void checkMode(List<String> playerCommand) {
        int typeHand;
        check = new Illegal();

        //check which command we are handling
       for (int i = 0; i < playerCommand.size(); i++){
           if (playerCommand.get(i).equals("b") && check.IsLegalBet()){//BET
               check.IllegalBet();//player can't bet again
               check.LegalBetValue();
               check.LegalDeal();

               if(isNumeric(playerCommand.get(i+1))){ //check if the player is betting something
                   if (intValue > 0 && intValue <= 5) {
                       player.betValue = intValue;
                   }else {
                       System.out.println("-cmd b " + intValue);
                       System.out.println("b: illegal amount");
                       check.IllegalBetValue();
                       check.LegalBet();//bet didn't occur
                       check.IllegalDeal();
                   }
                   i++;
               }

               if (check.IsLegalBetValue()) {//betValue == -1
                   System.out.println("-cmd b " + player.betValue);
                   System.out.println("player is betting: " + player.betValue);
                   player.loseCredit(player.betValue);
                   betCount+=player.betValue;
               }
               System.out.println();
           } else if (playerCommand.get(i).equals("$")) {//CREDIT
               System.out.println("-cmd "+playerCommand.get(i));
               System.out.println("player's credit is: " + player.credit);
               System.out.println();

           } else if (playerCommand.get(i).equals("d") && check.IsLegalDeal()) {//DEAL

               //assuming all needed commands are illegal until verified
               check.IllegalBet();
               check.IllegalDeal();
               check.LegalHold();

               player.hand.Clean();
               player.save.Clean();
               player.holdCards.clear();

               deckFile.deal5Cards(player);

               System.out.println("-cmd "+playerCommand.get(i));
               System.out.println("player's hand: " + player.hand.cards);
               System.out.println();
           } else if (playerCommand.get(i).equals("h") && check.IsLegalHold()) {//HOLD
               //assuming all needed commands are illegal until verified
               check.LegalBet();//Bet = -1;
               check.IllegalHold();//Hold = 1;
               check.IllegalDeal();
                player.holdCards.clear();
               while (isNumeric(playerCommand.get(i+1))){ //check which cards the player are holding
                   player.holdCards.add(intValue);
                   i++;
                   if (i == playerCommand.size()-1) {
                       break;
                   }
               }

               deckFile.getNewPlayerHand(player);
               System.out.println("-cmd h " + player.holdCards);
               System.out.println("player's hand: " + player.hand.toString());

               doublebonus = new DoubleBonus(player);
               typeHand = doublebonus.TypeOfHand();

               if (typeHand != -1) {
                   player.winCredit(doublebonus.multipliers[typeHand] * player.betValue);
                   System.out.println("player wins with a " + doublebonus.winningHands[typeHand].toUpperCase() + " and his credit is " + player.credit);
               } else{
                   System.out.println("player loses and his credit is " + player.credit);
               }
               System.out.println();
           }
           else if (playerCommand.get(i).equals("a")) {
               doublebonus = new DoubleBonus(player);
               doublebonus.advice();
               System.out.println("-cmd "+playerCommand.get(i));
               System.out.println("Player should hold: " + player.holdCards);
               System.out.println();
           }
           else if (playerCommand.get(i).equals("s")) {
               doublebonus = new DoubleBonus(player);
               System.out.println("-cmd "+playerCommand.get(i));
               doublebonus.statistics();

               double balance = (double)player.sumGains/ betCount*100;
               System.out.println("Credit\t\t\t\t"+player.credit + "(" + balance +"%)");
           }else {
               //System.out.println("Command: "+playerCommand.get(i)+" " + check.bet);
               System.out.println("Illegal Command");
           }
       }
    }

    /**
     * Method to check if a given string is a numeric value.
     * @param string to verify
     * @return boolean
     */
    public boolean isNumeric(String string){
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }
}
