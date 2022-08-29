package VideoPoker;

/**
 * It is used for errors/exceptions related to Card and Deck objects
 * @author Joao Gomes, Matilde Oliva, Miguel Vicente
 */
public class PlayingCardException extends Exception {

    PlayingCardException ( String reason ){
        super (reason);
    }
}