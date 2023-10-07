package capitaly;

import capitaly.player.Player;
import capitaly.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Track {
    private final List<Tile> tiles;
    private final int length;

    private final Map<Player, Integer> positions;

    public Track(List<Tile> tiles) {
        this.tiles = new ArrayList<>(tiles);
        this.length = this.tiles.size();
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
}
