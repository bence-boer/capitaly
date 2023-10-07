package capitaly.util;

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

public final class InputDataParser {
    private final Track track;
    private final List<Player> players;
    private final int[] diceRolls;

    public InputDataParser(String path) throws InvalidInputException {
        try (Scanner scanner = new Scanner(new File(path))) {
            this.track = new Track(readList(scanner, InputDataParser::parseTile));
            this.players = readList(scanner, InputDataParser::parsePlayer);
            this.diceRolls = readList(scanner, InputDataParser::parseDiceRoll)
                .stream()
                .mapToInt(i -> i)
                .toArray();
        } catch (FileNotFoundException exception) {
            throw new InvalidInputException("No input file found at " + path);
        }
    }

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

    private static int parseDiceRoll(String line) throws InvalidInputException {
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

    public Track getTrack() {
        return this.track;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int[] getDiceRolls() {
        return diceRolls;
    }
}
