package capitaly.tile;

import capitaly.player.Player;

/**
 * Represents a tile that gives the player a reward.
 */
public final class LuckTile extends Tile {
    /**
     * The reward the player receives when they enter the tile.
     */
    private final int reward;

    /**
     * Constructs a new LuckTile with the given reward.
     *
     * @param reward The reward the player receives when they enter the tile.
     */
    public LuckTile(int reward) {
        this.reward = reward;
    }

    /**
     * Gives the reward to the player.
     * @param player The player that enters the tile.
     */
    @Override
    public void enter(Player player) {
        player.receive(this.reward);
    }

    /**
     * Returns the string representation of the tile.
     *
     * @return The string describing the tile.
     */
    @Override
    public String toString() {
        return super.toString() + " LuckTile ( +" + reward + " )";
    }
}
