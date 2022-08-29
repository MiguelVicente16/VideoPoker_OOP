package VideoPoker;


/**
 * Class that implements the Video Poker variant double bonus
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */

public class DoubleBonus extends Rules{
    // Player object
    protected Player player;

    //default constant payout multiplier value and player hand types
    protected final int[] multipliers = {1,1,3,5,7,10,50,80,160,50,250};
    protected final String[] winningHands = {"Jacks or Better", "Two Pair","Three of a Kind","Straight","Flush",
            "Full House","Four 5-k","Four 2-4","Four Aces","Straight Flush","Royal Flush"};

    /**
     * Constructor that creates a double bonus variant, and sort the hand
     * @param player the actual player
     */
    public DoubleBonus(Player player) {
        player.hand.sortHand();
        this.player = player;
    }

    /**
     * Method that checks if the player's hand is one of the winning hands
     * @return the value of the type of hand, if it is a winning one, or -1 if not
     */
    public int TypeOfHand(){

        if(isRoyalFlush(player.hand, player.holdCards)){
            player.handStatistics[9]++;
            player.handStatistics[10]++;
            return 10;
        }
        else if(isStraightFlush(player.hand, player.holdCards)){
            player.handStatistics[8]++;
            player.handStatistics[10]++;
            return 9;
        }
        else if((isFourOfaKind(player.hand, player.save, player.holdCards) == 1)){
            player.handStatistics[7]++;
            player.handStatistics[10]++;
            return 8;

        }
        else if(isFourOfaKind(player.hand, player.save, player.holdCards) == 2){
            player.handStatistics[7]++;
            player.handStatistics[10]++;
            return 7;
        }
        else if(isFourOfaKind(player.hand, player.save, player.holdCards) == 3){
            player.handStatistics[7]++;
            player.handStatistics[10]++;
            return 6;
        }
        else if(isFullHouse(player.hand, player.holdCards)){
            player.handStatistics[6]++;
            player.handStatistics[10]++;
            return 5;
        }
        else if(isFlush(player.hand, player.holdCards)){
            player.handStatistics[5]++;
            player.handStatistics[10]++;
            return 4;
        }
        else if(isStraight(player.hand, player.holdCards)){
            player.handStatistics[4]++;
            player.handStatistics[10]++;
            return 3;
        }
        else if(isThreeOfaKind(player.hand, player.save, player.holdCards) != -1){
            player.handStatistics[3]++;
            player.handStatistics[10]++;
            return 2;
        }
        else if(isTwoPair(player.hand, player.save, player.holdCards)){
            player.handStatistics[2]++;
            player.handStatistics[10]++;
            return 1;
        }
        else if(isOneOfAKind(player.hand, player.save, player.holdCards) == 1){
            player.handStatistics[1]++;
            player.handStatistics[10]++;
            return 0;
        }
        else {
            player.handStatistics[0]++;
            player.handStatistics[10]++;
            return -1;
        }
    }

    /**
     * Method that classifies the hand and indicates which cards the player should hold
     */
    public void advice(){

        if (isRoyalFlush(player.hand, player.holdCards)){
            return;
        } else if (isStraightFlush(player.hand, player.holdCards)){
            return;
        } else if (isFourOfaKind(player.hand, player.save, player.holdCards) != -1){
            return;
        } else if (is4toRoyal(player.hand, player.save, player.holdCards)) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        } else if (isThreeOfaKind(player.hand, player.save, player.holdCards) == 1) {
            return;
        } else if (isFullHouse(player.hand, player.holdCards)) {
            return;
        } else if (isStraight(player.hand, player.holdCards)) {
            return;
        } else if (isFlush(player.hand, player.holdCards)) {
            return;
        }else if (isThreeOfaKind(player.hand, player.save, player.holdCards) == 2) {
            return;
        } else if ((is4toOutside(player.hand, player.holdCards) == 106) || (is4toInsideStraight(player.hand, player.holdCards) == 106)) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        } else if (isTwoPair(player.hand, player.save, player.holdCards)) {
            return;
        } else if (isOneOfAKind(player.hand, player.save, player.holdCards) == 1) {
            return;
        } else if (is4toFlush(player.hand, player.save, player.holdCards) == 1) {
            return;
        } else if (is3toRoyalFlush(player.hand, player.save, player.holdCards) == 1) {
            return;
        } else if (is4toOutside(player.hand, player.holdCards) == 111) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        } else if (isOneOfAKind(player.hand, player.save, player.holdCards) == 2) {
            return;
        }else if (isAKQJUnsuited(player.hand, player.save, player.holdCards) == 1) {
            return;
        } else if (is3toStraightFlush(player.hand, player.save, player.holdCards) == 114) {
            return;
        } else if (is4toInsideStraight(player.hand, player.holdCards) == 115) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        } else if (isQJSuited(player.hand, player.save, player.holdCards) == 116) {
            return;
        }  else if (is3toFlush(player.hand, player.save, player.holdCards) == 117) {
            return;
        }  else if (is2SuitedHigh(player.hand, player.save, player.holdCards) == 118) {
            return;
        } else if (is4toInsideStraight(player.hand, player.holdCards) == 119) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        } else if (is3toStraightFlush(player.hand, player.save, player.holdCards) == 120) {
            return;
        } else if (is4toInsideStraight(player.hand, player.holdCards) == 121) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        } else if (isKQJUnsuited(player.hand, player.save, player.holdCards) == 122) {
            return;
        } else if (isJTSuited(player.hand, player.save, player.holdCards) == 123) {
            return;
        } else if (isQJSuited(player.hand, player.save, player.holdCards) == 124) {
            return;
        }  else if (is3toFlush(player.hand, player.save, player.holdCards) == 125) {
            return;
        } else if (isQTSuited(player.hand, player.save, player.holdCards) == 126) {
            return;
        } else if (is3toStraightFlush(player.hand, player.save, player.holdCards) == 127) {
            return;
        } else if (isKQ_KJUnsuited(player.hand, player.save, player.holdCards) == 128) {
            return;
        } else if (isAce(player.hand, player.save, player.holdCards) == 129) {
            return;
        } else if (isKTSuited(player.hand, player.save, player.holdCards) == 130) {
            return;
        } else if (isKQJ(player.hand, player.save, player.holdCards) == 131) {
            return;
        } else if (is4toInsideStraight(player.hand, player.holdCards) == 132) {
            whichToHold(player.hand, player.save, player.holdCards);
            return;
        }else if (is3toFlush(player.hand, player.save, player.holdCards) == 133) {
            return;
        }else{
            return;
        }
    }

    /**
     * Method that prints the final statistics of the game
     */
    public void statistics(){
        System.out.println("Hand \t\t\t\tNb");
        System.out.println("Jacks or Better \t\t"+player.handStatistics[1]);
        System.out.println("Two Pair \t\t\t"+player.handStatistics[2]);
        System.out.println("Three of a Kind \t\t"+player.handStatistics[3]);
        System.out.println("Straight\t\t\t"+player.handStatistics[4]);
        System.out.println("Flush\t\t\t\t"+player.handStatistics[5]);
        System.out.println("Full House\t\t\t"+player.handStatistics[6]);
        System.out.println("Four of a kind\t\t\t"+player.handStatistics[7]);
        System.out.println("Straight Flush\t\t\t"+player.handStatistics[8]);
        System.out.println("Royal Flush\t\t\t"+player.handStatistics[9]);
        System.out.println("Other\t\t\t\t"+player.handStatistics[0]);
        System.out.println("Total\t\t\t\t"+player.handStatistics[10]);
    }

}
