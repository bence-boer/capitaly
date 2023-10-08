package capitaly;

import capitaly.player.Player;
import capitaly.io.InputDataParser;
import capitaly.io.InvalidInputException;

import java.util.*;

/**
 * Represents the Capitaly game.
 */
public final class Capitaly {
    /**
     * The paths of the test files.
     */
    private static final String[] TEST_FILE_PATHS = {
        "test/test_01.txt",  // test real-estate purchase/upgrade/fee mechanics
        "test/test_02.txt",  // test player type decision-making methods
        "test/test_03.txt",  // test player elimination and winning
        "test/test_04.txt",  // test invalid fee
        "test/test_05.txt",  // test invalid data count
        "test/test_06.txt",  // test manual play
    };

    /**
     * The track of the game.
     */
    private final Track track;

    /**
     * The players playing the game.
     */
    private final List<Player> players;

    /**
     * The index of the current player.
     */
    private int currentPlayerIndex;

    /**
     * Constructs a new Capitaly game with the given track and players.
     *
     * @param track   The track of the game.
     * @param players The players playing the game.
     */
    public Capitaly(Track track, List<Player> players) {
        this.track = track;
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }

    /**
     * The entry point of the program.
     * Starts the game with the given arguments.
     *
     * @param args The arguments of the program (the path of the input file).
     * @throws InvalidInputException If the input is invalid.
     */
    public static void main(String[] args) throws InvalidInputException {
        String path = args.length > 0 ? args[0] : TEST_FILE_PATHS[0];
        InputDataParser parser = new InputDataParser(path);
        Capitaly game = new Capitaly(parser.getTrack(), parser.getPlayers());

        int[] rolls = parser.getDiceRolls();
        if (rolls == null) game.manualGame();
        else game.simulateGame(rolls);
    }

    /**
     * Starts the game in manual mode.
     * The user can roll the die manually.
     * The game ends when there is only one player left.
     */
    private void manualGame() {
        System.out.println("Manual mode");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("-------------------------------------------------------------");
            System.out.print(players.get(currentPlayerIndex % players.size()).getName() + ": ");
            if (scanner.hasNextLine()) {
                try {
                    int value = InputDataParser.parseDiceRoll(scanner.nextLine());
                    this.progress(value);
                    this.info();
                } catch (InvalidInputException exception) {
                    System.out.println("\nPlease provide an integer between 1 and 6 for the dice roll!");
                }
            }
        }
    }

    /**
     * Starts the game in simulation mode.
     *
     * @param rolls The rolls of the die.
     */
    private void simulateGame(int[] rolls) {
        System.out.println("Simulation mode");
        this.progress(rolls);
        this.info();
    }

    /**
     * Progresses the game with the given roll, rolled by the current player.
     *
     * @param roll The roll of the die.
     */
    private void progress(int roll) {
        if (this.currentPlayerIndex >= this.players.size()) this.currentPlayerIndex = 0;
        Player player = this.players.get(this.currentPlayerIndex++);
        track.progress(player, roll);
        if (player.isBankrupt()) this.eliminate(player);

        if (players.size() == 1) {
            System.out.println(players.get(0).getName() + " won the game!");
            this.info();
            System.exit(0);
        }
    }

    /**
     * Progresses the game with the given rolls.
     *
     * @param rolls The rolls of the die.
     */
    private void progress(int[] rolls) {
        for (Integer roll : rolls) this.progress(roll);
    }

    /**
     * Eliminates the given player from the game.
     *
     * @param player The player to eliminate.
     */
    private void eliminate(Player player) {
        player.lose();
        this.track.remove(player);
        this.players.remove(player);
        System.out.println(player.getName() + " got eliminated.");
    }

    /**
     * Prints the information about the players still in the game.
     */
    private void info() {
        for (Player player : this.players) {
            System.out.println(player);
        }
    }
}
