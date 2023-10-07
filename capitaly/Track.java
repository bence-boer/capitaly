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

    private final Map<Player, Integer> players;

    public Track(List<Tile> tiles) {
        this.tiles = new ArrayList<>(tiles);
        this.length = this.tiles.size();

        this.players = new HashMap<>();
    }

    public void progress(Player player, int steps) {
        if(!players.containsKey(player)) players.put(player, -1);
        int targetIndex = (players.get(player) + steps) % this.length;
        players.put(player, targetIndex);
        player.stepOn(tiles.get(targetIndex));
    }

    public void remove(Player player) {
        players.remove(player);
    }
}
