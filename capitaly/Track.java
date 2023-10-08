package capitaly;

import capitaly.player.Player;
import capitaly.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents the track of the game.
 */
public class Track {
    /**
     * The length of the track (the number of tiles).
     */
    private final int length;

    /**
     * The tiles on the track.
     */
    private final List<Tile> tiles;

    /**
     * The positions of the players on the track.
     */
    private final Map<Player, Integer> positions;

    /**
     * Constructs a new Track with the given tiles.
     *
     * @param tiles The tiles on the track.
     */
    public Track(List<Tile> tiles) {
        this.length = tiles.size();
        this.tiles = new ArrayList<>(tiles);
        this.positions = new HashMap<>();
    }

    /**
     * progress
     * Moves the player on the track.
     *
     * @param player The player to move.
     * @param steps  The number of steps to move.
     */
    public void progress(Player player, int steps) {
        positions.putIfAbsent(player, -1);
        int target = (positions.get(player) + steps) % this.length;

        positions.replace(player, target);
        tiles.get(target).enter(player);
    }

    /**
     * Removes the player from the track.
     *
     * @param player The player to remove.
     */
    public void remove(Player player) {
        positions.remove(player);
    }

    /**
     * Returns the string representation of the track.
     *
     * @return The string describing the track.
     */
    @Override
    public String toString() {
        return "Track {" +
            "\n\tlength: " + length +
            "\n\ttiles: " +
            tiles.stream().map(tile -> "\n\t\t" + tile).collect(Collectors.joining()) +
            "\n\tpositions: " +
            positions +
            "\n}";
    }
}
