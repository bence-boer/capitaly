package capitaly;

import capitaly.player.Player;
import capitaly.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Track {
    private final int length;
    private final List<Tile> tiles;

    private final Map<Player, Integer> positions;

    public Track(List<Tile> tiles) {
        this.length = tiles.size();
        this.tiles = new ArrayList<>(tiles);
        this.positions = new HashMap<>();
    }

    public void progress(Player player, int steps) {
        positions.putIfAbsent(player, -1);
        int target = (positions.get(player) + steps) % this.length;

        positions.replace(player, target);
        tiles.get(target).enter(player);
    }

    public void remove(Player player) {
        positions.remove(player);
    }

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
