package capitaly.io;

import capitaly.Track;
import capitaly.player.CarefulPlayer;
import capitaly.player.GreedyPlayer;
import capitaly.player.Player;
import capitaly.player.StrategicPlayer;
import capitaly.tile.LuckTile;
import capitaly.tile.RealEstateTile;
import capitaly.tile.ServiceTile;
import capitaly.tile.Tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

/**
 * Responsible for parsing the input data.
 */
public final class InputDataParser {

    /**
     * The track described in the input file.
     */
    private final Track track;
    /**
     * The list of players specified in the input file.
     */
    private final List<Player> players;

    /**
     * The list of dice rolls specified in the input file.
     */
    private final int[] diceRolls;

    /**
     * Constructs a new InputDataParser object.
     *
     * @param path The path to the input file.
     * @throws InvalidInputException If the input file is invalid.
     */
    public InputDataParser(String path) throws InvalidInputException {
        try (Scanner scanner = new Scanner(new File(path))) {
            this.track = new Track(readList(scanner, InputDataParser::parseTile));
            this.players = readList(scanner, InputDataParser::parsePlayer);
            this.diceRolls = !scanner.hasNextLine() ?
                null :
                readList(scanner, InputDataParser::parseDiceRoll)
                    .stream()
                    .mapToInt(i -> i)
                    .toArray();

        } catch (FileNotFoundException exception) {
            throw new InvalidInputException("No input file found at " + path);
        }
    }

    /**
     * Reads a list of objects from the input file.
     *
     * @param scanner    The scanner to read from.
     * @param lineParser The function to parse a single line.
     * @param <TYPE>     The type of the objects in the list.
     * @return The list of objects.
     * @throws InvalidInputException If the input file is invalid.
     */
    private static <TYPE> List<TYPE> readList(Scanner scanner, Function<String, TYPE> lineParser) throws InvalidInputException {
        int counter = 0;
        int dataCount = 0;
        String line = null;

        try {
            line = scanner.nextLine();
            dataCount = Integer.parseInt(line);
            final List<TYPE> result = new ArrayList<>(dataCount);
            while (counter++ < dataCount) result.add(lineParser.apply(scanner.nextLine()));
            return result;
        } catch (NoSuchElementException exception) {
            throw new InvalidInputException("Invalid input while reading input:\nExpected " + dataCount + " lines of type, found only " + counter + ".");
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Invalid input while reading input:\nExpected an integer value for data count, found \"" + line + "\".");
        }
    }

    /**
     * Parses a tile from the input file.
     *
     * @param line The line to parse.
     * @return The parsed tile.
     * @throws InvalidInputException If the string is not a valid tile.
     */
    private static Tile parseTile(String line) throws InvalidInputException {
        if (line == null || line.isBlank())
            throw new InvalidInputException("Invalid input while parsing tile:\nTrying to parse blank string.");
        final String[] tokens = line.split(" ");
        if (tokens.length != 2 && tokens.length != 1)
            throw new InvalidInputException("Invalid input while parsing tile:\nExpected 1 or 2 tokens, got " + tokens.length + " instead: " + Arrays.toString(tokens));

        try {
            return switch (tokens[0]) {
                case "realestate" -> new RealEstateTile();
                case "service" -> new ServiceTile(Integer.parseInt(tokens[1]));
                case "luck" -> new LuckTile(Integer.parseInt(tokens[1]));
                default ->
                    throw new InvalidInputException("Invalid input while parsing tile:\nExpected < \"realestate\" | \"service\" | \"luck\" > [ amount: int ], got " + Arrays.toString(tokens) + " instead.");
            };
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Invalid input while parsing tile:\nExpected < \"realestate\" | \"service\" | \"luck\" > [ amount: int ], got " + Arrays.toString(tokens) + " instead.");
        }
    }

    /**
     * Parses a player from the input file.
     *
     * @param line The line to parse.
     * @return The parsed player.
     * @throws InvalidInputException If the string is not a valid player.
     */
    private static Player parsePlayer(String line) throws InvalidInputException {
        if (line == null || line.isBlank())
            throw new InvalidInputException("Invalid input while parsing player:\nTrying to parse blank string.");
        final String[] tokens = line.split(" ");
        if (tokens.length != 2)
            throw new InvalidInputException("Invalid input while parsing player:\nExpected 2 tokens, got " + tokens.length + " instead: " + Arrays.toString(tokens));

        return switch (tokens[1]) {
            case "careful" -> new CarefulPlayer(tokens[0]);
            case "strategic" -> new StrategicPlayer(tokens[0]);
            case "greedy" -> new GreedyPlayer(tokens[0]);
            default ->
                throw new InvalidInputException("Invalid input while parsing player:\nExpected < \"careful\" | \"strategic\" | \"greedy\" > < name: String >, got " + Arrays.toString(tokens) + " instead.");
        };
    }

    /**
     * Parses a die roll from the input file.
     *
     * @param line The line to parse.
     * @return The parsed dice roll.
     * @throws InvalidInputException If the string is not a valid dice roll.
     */
    public static int parseDiceRoll(String line) throws InvalidInputException {
        if (line == null || line.isBlank())
            throw new InvalidInputException("Invalid input while parsing dice roll:\nTrying to parse blank string.");
        final String[] tokens = line.split(" ");
        if (tokens.length != 1)
            throw new InvalidInputException("Invalid input while parsing dice roll:\nExpected 1 token, got " + tokens.length + " instead: " + Arrays.toString(tokens));

        int result;
        try {
            result = Integer.parseInt(line);
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Invalid input while parsing dice roll:\nExpected < 1 | 2 | 3 | 4 | 5 | 6 >, got \"" + line + "\" instead.");
        }
        return switch (result) {
            case 1, 2, 3, 4, 5, 6 -> result;
            default ->
                throw new InvalidInputException("Invalid input while parsing dice roll:\nExpected < 1 | 2 | 3 | 4 | 5 | 6 >, got \"" + line + "\" instead.");
        };
    }

    /**
     * Returns the track described in the input file.
     *
     * @return The track described in the input file.
     */
    public Track getTrack() {
        return this.track;
    }

    /**
     * Returns the list of players specified in the input file.
     *
     * @return The list of players specified in the input file.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the list of dice rolls specified in the input file.
     *
     * @return The list of dice rolls specified in the input file.
     */
    public int[] getDiceRolls() {
        return diceRolls;
    }

    /**
     * Compares the InputDataParser to another object.
     *
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InputDataParser that)) return false;

        if (!track.equals(that.track)) return false;
        if (!players.equals(that.players)) return false;
        return Arrays.equals(diceRolls, that.diceRolls);
    }

    /**
     * Returns the hash code of the InputDataParser.
     *
     * @return The hash code of the InputDataParser.
     */
    @Override
    public int hashCode() {
        int result = track.hashCode();
        result = 31 * result + players.hashCode();
        result = 31 * result + Arrays.hashCode(diceRolls);
        return result;
    }

    /**
     * Returns the string representation of the InputDataParser.
     *
     * @return The string describing the parser.
     */
    @Override
    public String toString() {
        return "InputDataParser {" +
            "\ntrack=" + track +
            ",\n players=" + players +
            ",\n diceRolls=" + Arrays.toString(diceRolls) +
            "\n}";
    }
}
