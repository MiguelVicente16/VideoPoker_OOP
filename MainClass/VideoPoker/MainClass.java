package VideoPoker;

/**
 * Main class that will implement the Double Bonus 10/7 variant of the Video Poker game
 * @author Joao Gomes(96248), Matilde Oliva(96283), Miguel Vicente(96288)
 */
public class MainClass {

    /**
     * Main method that manages the game modes according to command line arguments
     * @param args Arguments used to call the program
     */
    public static void main(String[] args) throws PlayingCardException {

        if (args[0].equals("-d")){
            if (args.length != 4){
                helpUser();
            }
            new DebugMode(args[1], args[2], args[3]);
        } else if (args[0] .equals("-s")) {
            new SimulationMode(args[1], args[2], args[3]);
        } else {
            helpUser();
        }
    }

    /**
     * Prints the correct usage of the program in case of bad usage
     */
    private static void helpUser() {
        System.out.println("helpUser:\n"
                + "\tjava -jar MainClass.jar -d(debug) credit cmd-file card-file\n"
                + "\tjava -jar MainClass.jar -s(simulation) credit bet nbdeal\n"
                + "where:\n"
                + "\tcredit is the initial credit and c > 0;\n"
                + "\tcmd-file is the name of the file containing the commands to debug\n"
                + "\tcard-file is the name of the file containing the cards to debug\n"
                + "\tbet the amount of each bet in simulation mode\n"
                + "\tnbdeals the number of deals in simulation mode\n");
        System.exit(-1);
    }
}
