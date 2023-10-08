package capitaly.tile;

import capitaly.player.Player;

/**
 * Represents a tile that takes a fee from the player.
 */
public final class ServiceTile extends Tile {
    /**
     * The fee the player pays when they enter the tile.
     */
    private final int fee;

    /**
     * Constructs a new ServiceTile with the given fee.
     *
     * @param fee The fee the player pays when they enter the tile.
     */
    public ServiceTile(int fee) {
        this.fee = fee;
    }

    /**
     * Takes the fee from the player.
     * @param player The player that enters the tile.
     */
    @Override
    public void enter(Player player) {
        player.pay(this.fee);
    }

    /**
     * Returns the string representation of the tile.
     *
     * @return The string describing the tile.
     */
    @Override
    public String toString() {
        return super.toString() + " ServiceTile ( -" + fee + " )";
    }
}
