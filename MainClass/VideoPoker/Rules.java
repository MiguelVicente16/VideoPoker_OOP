package VideoPoker;

import java.util.*;

/**
 * Abstract class that implements the rules for Video Poker variant double bonus
 * and gives the advices to play the perfect strategy
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public abstract class Rules {


    /** checks if the hand is a Royal Flush
     * Method that
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return true if it is, false if not
     */
    protected boolean isRoyalFlush(Hand hand, ArrayList<Integer> holdCards){
        String firstCardSuit = hand.Get(0).getCardSuit();
        List<String> royalFlushRankList = Arrays.asList("A","T","J","Q","K");

        for(Card card : hand.cards){
            if(!Objects.equals(card.getCardSuit(), firstCardSuit) || !royalFlushRankList.contains(card.getCardRank()))
                return false;
        }
        holdCards.addAll(Arrays.asList(1,2,3,4,5));
        return true;
    }

    /**
     * Method that checks if the hand is a Straight Flush
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return true if it is or false if it is not
     */
    protected boolean isStraightFlush(Hand hand, ArrayList<Integer> holdCards){
        String firstCardSuit = hand.Get(0).getCardSuit();
        List<Integer> sortedCardRanks = new ArrayList<>();
        int straight = 1;

        holdCards.clear();

        //Create an Integer list containing all the player's ranks
        for(Card card : hand.cards){
            sortedCardRanks.add(card.getCardRankValue());
        }
        //Sort the ranks
        Collections.sort(sortedCardRanks); //sq já não é preciso

        //Check to see that all card suits are identical
        for(Card card : hand.cards){
            if(!Objects.equals(card.getCardSuit(), firstCardSuit))
                return false;
        }

        //Go Step by step to see if the next card's rank is only 1 more than it
        for(int i = 0; i < 4; i++) {
            if (!(sortedCardRanks.get(i) == (sortedCardRanks.get(i + 1) - 1))){
                straight = 0;
                break;
            }
        }
        if(straight == 0){
            for(int i = 0; i < 4; i++){
                if(!(sortedCardRanks.get(i) == i+2)) {
                    return false;
                }
            }
            if(sortedCardRanks.get(4) == 14) {
                holdCards.addAll(Arrays.asList(1,2,3,4,5));
                return true;
            }
        }
        holdCards.addAll(Arrays.asList(1,2,3,4,5));
        return true;
    }
    /**
     * Method that checks if the hand is a two, three or four of a kind
     * @param hand the poker hand to classify
     * @param nSameRanks number of cards required to be of the same rank
     * @return the value of the type of hand or -1 if it is not none
     */
    protected int isOfAKind(Hand hand, int nSameRanks) {
        int key = 0;
        HashMap<Integer,Integer> rankMap = new HashMap<>();

        for(Card card : hand.cards){
            if(!rankMap.containsKey(card.getCardRankValue())){
                rankMap.put(card.getCardRankValue(), 1);
            }
            else{
                int value = rankMap.get(card.getCardRankValue());
                rankMap.put(card.getCardRankValue(), value+1);
            }
        }

        if (rankMap.containsValue(nSameRanks)) {
            for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
                // if give value is equal to value from entry
                // print the corresponding key
                if (entry.getValue() == nSameRanks) {
                    key = entry.getKey();
                    break;
                }
            }
            if (nSameRanks == 4) {
                if(key == 14){
                    return 1;
                } else if (key >= 2 && key <= 4) {
                    return 2;
                } else if (key >= 5 && key <= 13) {
                    return 3;
                }
            } else if (nSameRanks == 3) {
                if (key == 14) {
                    return key;
                }else {
                    return key;
                }
            } else if (nSameRanks == 2) {
                return key;
            }
        }
        return -1;
    }

    /**
     * Method that verify how much cards have the same Suit
     * @param hand the poker hand to classify
     * @param nSameSuits number of cards required to be of the same suit
     * @return the number of cards with the same Suit in the variable "suit"
     */
    protected int isOfASuit(Hand hand, int nSameSuits){
        HashMap<Integer,Integer> suitMap = new HashMap<>();
        int suit = 0;

        for(Card card : hand.cards){
            if(!suitMap.containsKey(card.getCardSuitValue())){
                suitMap.put(card.getCardSuitValue(), 1);
            }
            else{
                int value = suitMap.get(card.getCardSuitValue());
                suitMap.put(card.getCardSuitValue(), value + 1);
            }
        }

        if (suitMap.containsValue(nSameSuits)){
            for(Map.Entry<Integer, Integer> entry: suitMap.entrySet()) {
                // if give value is equal to value from entry
                // print the corresponding key
                if (entry.getValue() == nSameSuits) {
                    suit = entry.getKey();
                    break;
                }
            }
            return suit;
        }
        return -1;
    }

    /**
     * Method that saves the cards that are to hold in FourOfaKind hand type
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return 1 if 4OfaKind is 4Aces, 2 if is 2-5 or 3 if is 5-K
     */
    protected int isFourOfaKind(Hand hand, Hand save, ArrayList<Integer> holdCards){
        if (isOfAKind(hand, 4) == 1){
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardRankValue() == 14) {
                    holdCards.add(i + 1);
                }
            }
            return 1;
        }else if (isOfAKind(hand, 4) == 2) {
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardRankValue() <= 4 ||save.Get(i).getCardRankValue() >= 2) {
                    holdCards.add(i + 1);
                }
            }
            return 2;
        } else if (isOfAKind(hand, 4) == 3) {
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardRankValue() <= 13 ||save.Get(i).getCardRankValue() >= 5) {
                    holdCards.add(i + 1);
                }
            }
            return 3;
        } else{
            return -1;
        }
    }

    /**
     * Method that checks if the hand is a Full House (Hand consisting of 3 same ranks, and 2 other same ranks)
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return true if it is or false if it is not
     */
    protected boolean isFullHouse(Hand hand, ArrayList<Integer> holdCards){
        HashMap<Integer,Integer> rankMap = new HashMap<>();

        holdCards.clear();
        for(Card card : hand.cards){
            if(!rankMap.containsKey(card.getCardRankValue())){
                rankMap.put(card.getCardRankValue(), 1);
            }
            else{
                int value = rankMap.get(card.getCardRankValue());
                rankMap.put(card.getCardRankValue(), value+1);

            }
        }
        if(rankMap.containsValue(3) && rankMap.containsValue(2)){
            holdCards.addAll(Arrays.asList(1,2,3,4,5));
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method that checks if the hand is a Flush (Hand of entirely identical suits)
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return true if it is or false if it is not
     */
    protected boolean isFlush(Hand hand, ArrayList<Integer> holdCards){
        String firstCardSuit = hand.Get(0).getCardSuit();

        holdCards.clear();
        //Check to see that all card suits are identical
        for(Card card : hand.cards){
            if(!Objects.equals(card.getCardSuit(), firstCardSuit))
                return false;
        }
        holdCards.addAll(Arrays.asList(1,2,3,4,5));
        return true;
    }

    /**
     * Method that checks if the hand is a Straight (Consecutive cards of different suits)
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return true if it is or false if it is not
     */
    protected boolean isStraight(Hand hand, ArrayList<Integer> holdCards){
        List<Integer> sortedCardRanks = new ArrayList<>();
        int straight = 1;

        holdCards.clear();
        //Create an Integer list containing all the player's ranks
        for(Card card : hand.cards){
            sortedCardRanks.add(card.getCardRankValue());
        }
        //Sort the ranks
        Collections.sort(sortedCardRanks); //sq já não é preciso

        //Go Step by step to see if the next card's rank is only 1 more than it
        for(int i = 0; i < 4; i++) {
            if (!(sortedCardRanks.get(i) == (sortedCardRanks.get(i + 1) - 1))){
                straight = 0;
                break;
            }
        }
        if(straight == 0){
            for(int i = 0; i < 4; i++){
                if(!(sortedCardRanks.get(i) == i+2)) {
                    return false;
                }
            }
            if(sortedCardRanks.get(4) == 14){
                holdCards.addAll(Arrays.asList(1,2,3,4,5));
                return true;
            }
        }
        holdCards.addAll(Arrays.asList(1,2,3,4,5));
        return true;
    }

    /**
     * Method that saves the cards that are to hold in ThreeOfaKind hand type
     * @param hand the poker handplayer.saveplayer.ho to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return 1 if the hand is 3OfAces, else returns 2
     */
    //Hand consisting of either 3, 4 or N of same kinds of ranks
    protected int isThreeOfaKind(Hand hand, Hand save, ArrayList<Integer> holdCards) {
        int key;

        if (isOfAKind(hand, 3) == 14){
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardRankValue() == 14) {
                    holdCards.add(i + 1);
                }
            }
            return 1;
        } else if (((key = isOfAKind(hand, 3)) < 14) && key != -1) {
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardRankValue() == key) {
                    holdCards.add(i + 1);
                }
            }
            return 2;
        } else {
            return -1;
        }
    }
    /**
     * Method that checks if the hand is a two pair (hand consisting of two pairs of identical ranks, and 1 different rank)
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return true if it is or false if it is not
     */
    //Hand consisting of two pairs of identical ranks, and 1 different rank
    protected boolean isTwoPair(Hand hand, Hand save, ArrayList<Integer> holdCards){
        HashMap<Integer,Integer> rankMap = new HashMap<>();
        int pairCounter = 0;
        int key1 = 0;
        int key2 = 0;
        int first = 0;

        holdCards.clear();

        for(Card card : hand.cards){
            if(!rankMap.containsKey(card.getCardRankValue())){
                rankMap.put(card.getCardRankValue(), 1);
            }
            else{
                int value = rankMap.get(card.getCardRankValue());
                rankMap.put(card.getCardRankValue(), value+1);
                pairCounter++;
            }
        }

        for(Map.Entry<Integer, Integer> entry: rankMap.entrySet()) {
            // if give value is equal to value from entry
            // print the corresponding key
            if (entry.getValue() == 2) {
                if (first == 0) {
                    key1 = entry.getKey();
                    first = 1;
                } else {
                    key2 = entry.getKey();
                }

            }
        }

        if (pairCounter == 2 && rankMap.containsValue(1)) {
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardRankValue() == key1 || save.Get(i).getCardRankValue() == key2) {
                    holdCards.add(i + 1);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that checks if the hand is a two, three or four of a kind
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not none
     */
    protected int isOneOfAKind(Hand hand, Hand save, ArrayList<Integer> holdCards){
        int key;

        if((key = isOfAKind(hand, 2)) != -1){
            if(key >= 11){
                for (int i = 0; i < save.size(); i++) {
                    if (save.Get(i).getCardRankValue() == key) {
                        holdCards.add(i + 1);
                    }
                }
                return 1; //HighPair
            } else {
                for (int i = 0; i < save.size(); i++) {
                    if (save.Get(i).getCardRankValue() == key) {
                        holdCards.add(i + 1);
                    }
                }
                return 2; //LowPair
            }
        }
        return -1;
    }

    /**
     * Method that checks if the hand is a four to Royal Flush
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected boolean is4toRoyal(Hand hand, Hand save, ArrayList<Integer> holdCards){
        List<Integer> RoyalCardList = Arrays.asList(14,11,12,13,10);
        List<String> RoyalHold = Arrays.asList("A","J","Q","K","T");
        List<Integer> flushCards = new ArrayList<>();

        int royalCards = 0;
        int flushRoyal = 0;
        int suit;
        int j = 0;

        holdCards.clear();

        if ((suit = isOfASuit(hand, 5)) != -1){
            flushRoyal = 1;
        } else if ((suit = isOfASuit(hand, 4)) != -1){
            flushRoyal = 1;
        }

        for (Card card : hand.cards){
            if (card.getCardSuitValue() == suit){
                flushCards.add(card.getCardRankValue());
            }
        }

        for (int i = 0; i < flushCards.size()-1; i++) {
            if (RoyalCardList.contains(flushCards.get(i))) {
                royalCards++;
            }
            if (RoyalCardList.contains(flushCards.get(i+1)) && i == flushCards.size()-2) royalCards++;

        }

        if (royalCards >= 4 && flushRoyal == 1){
            while (j<5) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), RoyalHold.get(j)) && save.Get(i).getCardSuitValue() == suit) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return true;
        }
        return false;
    }

    /**
     * Method that checks if the handp is a four to Inside Straight. If so, checks if the four cards are a flush (four to straight flush) or counts the number of high cards.
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not none of this type
     */
    protected int is4toInsideStraight(Hand hand, ArrayList<Integer> holdCards){

        List<Integer> HighCardList = Arrays.asList(14,11,12,13);

        int straight = 0;
        int suit = 0;
        int flush = 0;
        int first = 0;
        int gaps = 0;
        int idx1 = 0;
        int idx2 = 0;
        int count = 0;
        int highCards = 0;

        holdCards.clear();

        for (int i = 0; i < hand.size()-1; i++) {
            if (hand.Get(i).getCardRankValue() == (hand.Get(i+1).getCardRankValue()-2)){
                if(idx1 == 0 && gaps == 0) {
                    idx1 = i;
                } else if (idx2 == 0 && gaps == 1) {
                    idx2 = i;
                }
                gaps++;
            }
        }

        if (gaps == 1) {
            if (idx1 == 0){
                for (int i = 1; i < hand.size() - 2; i++) {
                    if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                        count++;
                    }
                }
                if (count == 2){
                    holdCards.addAll(Arrays.asList(1,2,3,4));
                    straight = 1;
                }
            } else if (idx1 == 1) {
                if ((hand.Get(2).getCardRankValue() == (hand.Get(3).getCardRankValue() - 1)) && (hand.Get(3).getCardRankValue() == (hand.Get(4).getCardRankValue() - 1))) {
                    holdCards.addAll(Arrays.asList(2,3,4,5));
                    straight = 1;
                }
                else if ((hand.Get(0).getCardRankValue() == (hand.Get(1).getCardRankValue() - 1)) && (hand.Get(2).getCardRankValue() == (hand.Get(3).getCardRankValue() - 1))) {
                    holdCards.addAll(Arrays.asList(1,2,3,4));
                    straight = 1;
                } else {
                    for (int i = 2; i < hand.size() - 1; i++) {
                        if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                            count++;
                        }
                    }
                    if (count == 2){
                        holdCards.addAll(Arrays.asList(2,3,4,5));
                        straight = 1;
                    }
                }
            } else if (idx1 == 2) {
                if ((hand.Get(1).getCardRankValue() == (hand.Get(2).getCardRankValue() - 1)) && (hand.Get(3).getCardRankValue() == (hand.Get(4).getCardRankValue() - 1))) {
                    holdCards.addAll(Arrays.asList(2,3,4,5));
                    straight = 1;
                } else {
                    for (int i = 0; i < hand.size() - 3; i++) {
                        if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                            count++;
                        }
                    }
                    if (count == 2){
                        holdCards.addAll(Arrays.asList(1,2,3,4));
                        straight = 1;
                    }
                }
            } else if (idx1 == 3) {
                for (int i = 1; i < hand.size() - 2; i++) {
                    if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                        count++;
                    }
                }
                if (count == 2){
                    holdCards.addAll(Arrays.asList(2,3,4,5));
                    straight = 1;
                }
            }
        }
        else if (gaps == 2) {
            if (idx1 == 0 && idx2 == 1){
                for (int i = 2; i < hand.size() - 1; i++) {
                    if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                        count++;
                    }
                }
                if (count == 2){
                    holdCards.addAll(Arrays.asList(2,3,4,5));
                    straight = 1;
                }
            } else if (idx1 == 0 && idx2 == 2){
                if ((hand.Get(1).getCardRankValue() == (hand.Get(2).getCardRankValue() - 1)) && (hand.Get(3).getCardRankValue() == (hand.Get(4).getCardRankValue() - 1))){
                    holdCards.addAll(Arrays.asList(2,3,4,5));
                    straight = 1;
                }
            } else if (idx1 == 0 && idx2 == 3){
                for (int i = 1; i < hand.size() - 2; i++) {
                    if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                        count++;
                    }
                }
                if (count == 2){
                    holdCards.addAll(Arrays.asList(2,3,4,5));
                    straight = 1;
                }
            } else if (idx1 == 1 && idx2 == 3){
                if ((hand.Get(0).getCardRankValue() == (hand.Get(1).getCardRankValue() - 1)) && (hand.Get(2).getCardRankValue() == (hand.Get(3).getCardRankValue() - 1))){
                    holdCards.addAll(Arrays.asList(1,2,3,4));
                    straight = 1;
                }
            } else if (idx1 == 2 && idx2 == 3){
                for (int i = 0; i < hand.size() - 3; i++) {
                    if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)) {
                        count++;
                    }
                }
                if (count == 2){
                    holdCards.addAll(Arrays.asList(1,2,3,4));
                    straight = 1;
                }
            }
        }

        for (Integer holdCard : holdCards) {
            if (first == 0) {
                suit = hand.Get(holdCard - 1).getCardSuitValue();
                first = 1;
            } else {
                if (hand.Get(holdCard - 1).getCardSuitValue() == suit) {
                    flush++;
                }
            }
        }

        for (Integer holdCard : holdCards) {
            if (HighCardList.contains(hand.Get(holdCard-1).getCardRankValue())){
                highCards++;
            }
        }


        if (straight == 1) {
            if (flush == 3){
                return 106;
            }
            else if (highCards == 3){
                return 115;
            }
            else if (highCards == 2){
                return 119;
            }
            else if (highCards == 1){
                return 121;
            }
            else if (highCards == 0) {
                return 132;
            }
        }
        if (hand.Get(4).getCardRankValue() == 14 && hand.Get(0).getCardRankValue() == 2 && hand.Get(1).getCardRankValue() == 3 && hand.Get(2).getCardRankValue() == 4) {
            if (hand.Get(0).getCardSuitValue() == hand.Get(1).getCardSuitValue() && hand.Get(0).getCardSuitValue() == hand.Get(2).getCardSuitValue() && hand.Get(0).getCardSuitValue() == hand.Get(4).getCardSuitValue()) {
                holdCards.addAll(Arrays.asList(1,2,3,5));
                return 106;
            } else {
                holdCards.addAll(Arrays.asList(1,2,3,5));
                return 121;
            }
        }
        return -1;
    }

    /**
     * Method that checks if the hand is a four to Flush
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int is4toFlush(Hand hand, Hand save, ArrayList<Integer> holdCards){
        int key;

        holdCards.clear();

        if ((key = isOfASuit(hand, 4)) != -1){
            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardSuitValue() == key){
                    holdCards.add(i+1);
                }
            }
            return 1;
        } else{
            return -1;
        }
    }
    /**
     * Method that checks if the hand is a three to Royal Flush
     * @param hand the poker handplayer.saveplayer.ho to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int is3toRoyalFlush(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards = Arrays.asList("A","K","J","Q","T");
        int[]HighCardsVect;
        HighCardsVect = new int[5];
        int[]SuitsVect;
        SuitsVect = new int[4];
        int countHighCards = 0;
        int i;

        holdCards.clear();
        for (Card card: hand.cards) {
            if(HighCards.contains(card.getCardRank())) {
                i = card.getCardRankValue() - 10;
                HighCardsVect[i]++;
            }
        }
        for (Card card: hand.cards) {
            for (int j = 0; j <5; j++){
                if (card.getCardRankValue() == (j+10)){
                    SuitsVect[card.getCardSuitValue()]++;
                }
            }
        }

        for (int j = 0; j<5; j++){
            if (HighCardsVect[j] != 0){
                countHighCards++;

            }
        }

        for (int k = 0; k<4; k++){
            if (SuitsVect[k] == 3 && countHighCards >= 3){
                for (int j = 0; j < save.size(); j++) {
                    if (save.Get(j).getCardSuitValue() == k){
                        holdCards.add(j+1);
                    }
                }
                return 1;
            }
        }

        return -1;
    }
    /**
     * Method that checks if the hand is a four to Outside Straight. If so, checks if the four cards are a flush (four to straight flush)
     * @param hand the poker hand to classify
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not none
     */
    protected int is4toOutside(Hand hand, ArrayList<Integer> holdCards){

        List<Integer> flushCards = new ArrayList<>();
        List<Integer> straightCards = new ArrayList<>();

        int straight = 0;
        int flush = 0;
        int key;

        holdCards.clear();

        if ((key = isOfASuit(hand, 4)) != -1){
            for (Card card : hand.cards){
                if (card.getCardSuitValue() == key){
                    flushCards.add(card.getCardRankValue());
                }
            }
        }

        //Go Step by step to see if the next card's rank is only 1 more than it
        for(int i = 0; i < hand.size()-1; i++) {
            if (hand.Get(i).getCardRankValue() == (hand.Get(i + 1).getCardRankValue() - 1)){
                straightCards.add(hand.Get(i).getCardRankValue());
            }
        }

        if (hand.Get(hand.size()-2).getCardRankValue() == (hand.Get(hand.size()-1).getCardRankValue()-1)){
            straightCards.add(hand.Get(hand.size()-1).getCardRankValue());
        }

        for (int i = 0; i < straightCards.size()-1; i++) {
            if ((straightCards.get(i) == (straightCards.get(i+1)-1)) && (straightCards.get(i) != 13)){
                straight++;
            }
        }

        for (int i = 0; i < flushCards.size()-1; i++) {
            if (flushCards.get(i) == (flushCards.get(i + 1) - 1)) {
                flush++;
            }
        }

        if (straight == 3){
            if ((hand.Get(0).getCardRankValue() != hand.Get(1).getCardRankValue()-1)){
                holdCards.addAll(Arrays.asList(2,3,4,5));
            }else {
                holdCards.addAll(Arrays.asList(1,2,3,4));
            }
            if (flush == 3){
                return 106;
            }  else {
                return 111;
            }
        }

        return -1;
    }
    /**
     * Method that checks if the hand is a AKQJ unsuited
     * @param hand the poker handplayer.saveplayer.ho to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isAKQJUnsuited(Hand hand, Hand save, ArrayList<Integer> holdCards) {
        List<String> HighCards = Arrays.asList("A","J","Q","K");
        int AKQJunsuited = 0;
        int first = 1;
        String suit = null;
        int unsuited = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                AKQJunsuited++;
                if (first == 1) {
                    suit = card.getCardSuit();
                    first = 0;
                } else if (!Objects.equals(card.getCardSuit(), suit)){
                    unsuited++;
                }
            }
        }
        if ((AKQJunsuited == 4) && (unsuited != 0)){
            while (j<4) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 1;
        }
        return -1;
    }

    /**
     * Method that checks if the hand is a three to Straight Flus
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int is3toStraightFlush(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<Integer> HighCardList = Arrays.asList(14, 13, 12, 11);
        List<Integer> suited234 = Arrays.asList(2,3,4);
        List<Integer> aceLow = Arrays.asList(2,3,4,5);
        List<Integer> flushCards = new ArrayList<>();

        int excpetion234 = 0;
        int excpetionAce = 0;
        int gaps = 0;
        int count = 0;
        int flush = 0;
        int key;
        int highcard = 0;
        int ace = 0;
        int j = 0;

        holdCards.clear();

        if ((key = isOfASuit(hand, 3)) != -1){
            for (Card card : hand.cards){
                if (card.getCardSuitValue() == key){
                    flushCards.add(card.getCardRankValue());
                }
            }
            flush = 1;
        }

        for (int i = 0; i < flushCards.size()-1; i++) {
            if (flushCards.get(i) == (flushCards.get(i+1)-1)){
                count++;
            } else if (flushCards.get(i) == (flushCards.get(i+1)-2)) {
                count++;
                gaps++;
            } else if (flushCards.get(i) == (flushCards.get(i+1)-3)) {
                count++;
                gaps+=2;
            }
        }

        for (int i = 0; i < flushCards.size(); i++) {
            if (HighCardList.contains(flushCards.get(i))){
                highcard++;
            }
            if (flushCards.get(i) == 14){
                ace=1;
            }
            if (Objects.equals(flushCards.get(i), suited234.get(i))) {
                excpetion234++;
            }
        }

        if (ace == 1){
            for (Integer flushCard : flushCards) {
                if (aceLow.contains(flushCard)) {
                    excpetionAce++;
                }
            }
        }
        if(excpetionAce == 2){
            count++;
        }


        if (flush == 1 && count == 2) {
            while (j<3) {
                for (int i = 0; i < save.size(); i++) {
                    if (save.Get(i).getCardRankValue() == flushCards.get(j)) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            if (excpetion234 == 3) {
                return 120;
            } else if (excpetionAce == 2) {
                return 120;
            } else if (highcard >= gaps) {
                return 114;
            } else if (gaps == 1 || (gaps == 2 && highcard == 1)) {
                return 120;
            } else if (gaps == 2 && highcard == 0) {
                return 127;
            }
        }

        return -1;
    }

    /**
     * Method that checks if the hand is a QJ suited or unsuited
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isQJSuited(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards = Arrays.asList("J","Q");
        int QJsuited = 0;
        int first = 1;
        String suit = null;
        int suited = 0;
        int unsuited = 0;
        int j = 0;

        holdCards.clear();

        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                QJsuited++;
                if (first == 1) {
                    suit = card.getCardSuit();
                    first = 0;
                } else if (Objects.equals(card.getCardSuit(), suit)){
                    suited++;
                } else{
                    unsuited++;
                }
            }
        }
        if ((QJsuited == 2) && (suited == 1)){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 116;
        }
        if ((QJsuited == 2) && (unsuited == 1)){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 124;
        }
        return -1;
    }

    /**
     * Method that checks if the hand is a three to Flush and counts the high cards
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int is3toFlush(Hand hand, Hand save, ArrayList<Integer> holdCards) {

        List<String> HighCards = Arrays.asList("A","K","J","Q");
        int countHighCards = 0;
        int key;

        holdCards.clear();

        if ((key = isOfASuit(hand, 3)) != -1){
            for (Card card: hand.cards){
                if((HighCards.contains(card.getCardRank())) && (card.getCardSuitValue() == key) ) {
                    countHighCards++;
                }
            }

            for (int i = 0; i < save.size(); i++) {
                if (save.Get(i).getCardSuitValue() == key){
                    holdCards.add(i+1);
                }
            }
        }

        if (countHighCards == 2){
            return 117;
        } else if (countHighCards == 1){
            return 125;
        }else if (countHighCards == 0){
            return 133;
        }
        return -1;
    }

    /**
     * Method that checks if the hand is a two Suited High
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int is2SuitedHigh(Hand hand, Hand save, ArrayList<Integer> holdCards){
        HashMap<Integer,Integer> suitMap = new HashMap<>();
        List<String> HighCards = Arrays.asList("A","K","J","Q");
        int highCards = 0;
        int key = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                if(!suitMap.containsKey(card.getCardSuitValue())){
                    suitMap.put(card.getCardSuitValue(), 1);
                }
                else{
                    int value = suitMap.get(card.getCardSuitValue());
                    suitMap.put(card.getCardSuitValue(), value+1);
                }
            }
        }

        if(suitMap.containsValue(2)){
            for(Map.Entry<Integer, Integer> entry: suitMap.entrySet()) {
                if (entry.getValue() == 2) {
                    key = entry.getKey();
                    break;
                }
            }
            for (Card card: hand.cards){
                if((HighCards.contains(card.getCardRank())) && (card.getCardSuitValue() == key) ) {
                    highCards++;
                }
            }
        }

        if (highCards == 2){
            while (j<4) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j)) && (save.Get(i).getCardSuitValue() == key)) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 118;
        }
        return -1;
    }


    /**
     * Method that checks if the hand is a KQJ unsuited or a Jack, Queen or King
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isKQJUnsuited(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards = Arrays.asList("K","J","Q");
        int KQJsuited = 0;
        int first = 1;
        String suit = null;
        int unsuited = 0;
        int j = 0;

        holdCards.clear();

        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                KQJsuited++;
                if (first == 1) {
                    suit = card.getCardSuit();
                    first = 0;
                } else if (!Objects.equals(card.getCardSuit(), suit)){
                    unsuited++;
                }
            }
        }
        if ((KQJsuited == 3) && (unsuited != 0)){
            while (j<3) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 122;
        } else if (KQJsuited == 3) {
            while (j<3) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 131;
        }
        return -1;
    }

    /**
     * Method that checks if the hand is a JT suited
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isJTSuited(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards = Arrays.asList("J","T");
        int JTsuited = 0;
        int first = 1;
        String suit = "";
        int suited = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                JTsuited++;
                if (first == 1) {
                    suit = card.getCardSuit();
                    first = 0;
                } else if (Objects.equals(card.getCardSuit(), suit)){
                    suited++;
                }
            }
        }
        if ((JTsuited == 2) && (suited == 1)){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 123;
        }
        return -1;

    }

    /**
     * Method that checks if the hand is a QT suited
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isQTSuited(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards = Arrays.asList("T","Q");
        int QTsuited = 0;
        int first = 1;
        String suit = "";
        int suited = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                QTsuited++;
                if (first == 1) {
                    suit = card.getCardSuit();
                    first = 0;
                } else if (Objects.equals(card.getCardSuit(), suit)){
                    suited++;
                }
            }
        }
        if ((QTsuited == 2) && (suited == 1)){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 126;
        }
        return -1;
    }

    /**
     * Method that checks if the handplayer.saveplayer.ho is a KQ or KJ unsuited
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isKQ_KJUnsuited(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards_KQ = Arrays.asList("K","Q");
        List<String> HighCards_KJ = Arrays.asList("K","J");
        int KQunsuited = 0;
        int KJunsuited = 0;
        int first_KQ = 1;
        int first_KJ = 1;
        String suit = null;
        String suit2 = null;
        int unsuited_KQ = 0;
        int unsuited_KJ = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards_KQ.contains(card.getCardRank())) {
                KQunsuited++;
                if (first_KQ == 1) {
                    suit = card.getCardSuit();
                    first_KQ = 0;
                } else if (!Objects.equals(card.getCardSuit(), suit)){
                    unsuited_KQ++;
                }
            } if(HighCards_KJ.contains(card.getCardRank())){
                KJunsuited++;
                if (first_KJ == 1) {
                    suit2 = card.getCardSuit();
                    first_KJ = 0;
                } else if (!Objects.equals(card.getCardSuit(), suit2)){
                    unsuited_KJ++;
                }
            }
        }
        if (KQunsuited == 2 && unsuited_KQ == 1){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards_KQ.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 128;
        } else if (KJunsuited == 2 && unsuited_KJ == 1){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards_KJ.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 128;
        }
        return -1;
    }

    /**
     * Method that checks if the hand is an Ace
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isAce(Hand hand, Hand save, ArrayList<Integer> holdCards){

        holdCards.clear();
        List<String> HighCards = List.of("A");
        int Ace = 0;

        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                Ace++;
            }
        }
        if (Ace == 1){
            for (int i = 0; i < save.size(); i++) {
                if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(0))) {
                    holdCards.add(i + 1);
                    break;
                }
            }
            return 129;
        }
        return -1;

    }

    /**
     * Method that checks if the handplayer.saveplayer.ho is a KT suited
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isKTSuited(Hand hand, Hand save, ArrayList<Integer> holdCards){

        List<String> HighCards = Arrays.asList("T","K");
        int KTsuited = 0;
        int first = 1;
        String suit = "";
        int suited = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                KTsuited++;
                if (first == 1) {
                    suit = card.getCardSuit();
                    first = 0;
                } else if (Objects.equals(card.getCardSuit(), suit)){
                    suited++;
                }
            }
        }
        if ((KTsuited == 2) && (suited == 1)){
            while (j<2) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 130;
        }
        return -1;
    }


    /**
     * Method that checks if the hand has one J, Q or K (is a Jack, Queen or King)
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     * @return the value of the type of hand or -1 if it is not
     */
    protected int isKQJ(Hand hand, Hand save, ArrayList<Integer> holdCards){
        List<String> HighCards = Arrays.asList("K","J","Q");
        int KQJ = 0;
        int j = 0;

        holdCards.clear();
        for (Card card: hand.cards){
            if(HighCards.contains(card.getCardRank())) {
                KQJ++;
            }
        }

        if (KQJ == 1){
            while (j<3) {
                for (int i = 0; i < save.size(); i++) {
                    if (Objects.equals(save.Get(i).getCardRank(), HighCards.get(j))) {
                        holdCards.add(i + 1);
                    }
                }
                j++;
            }
            Collections.sort(holdCards);
            return 131;
        }
        return -1;
    }

    /**
     * Method that marks in an array the position of the cards to hold
     * @param hand the poker hand to classify
     * @param save copy of the poker hand
     * @param holdCards array list with the positions of cards to hold
     */
    protected void whichToHold(Hand hand, Hand save, ArrayList<Integer> holdCards){
        List<Integer> holdCardsAux = new ArrayList<>();

        for (Integer holdCard : holdCards) {
            for (int i = 0; i < save.size(); i++) {
                if(hand.Get(holdCard-1).equals(save.Get(i))){
                    holdCardsAux.add(i+1);
                }
            }
        }
        Collections.sort(holdCardsAux);

        holdCards.clear();
        holdCards.addAll(holdCardsAux);
    }
}
