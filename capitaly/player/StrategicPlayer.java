package capitaly.player;

import capitaly.tile.RealEstateTile;

/**
 * Represents a player that always acts strategically with their money.
 */
public class StrategicPlayer extends Player {

    /**
     * Whether the player wants to skip their turn.
     */
    private boolean skip;

    /**
     * Constructs a new StrategicPlayer with the given name.
     *
     * @param name The name of the player.
     */
    public StrategicPlayer(String name) {
        super(name);
        this.skip = false;
    }

    /**
     * Returns whether the player wants to buy the given tile.
     * A strategic player wants to buy a tile if it is the second tile they can buy in a row.
     *
     * @param tile The tile to buy.
     * @return Whether the player wants to buy the given tile.
     */
    @Override
    public final boolean wantsToBuy(RealEstateTile tile) {
        return this.canBuy(tile) && (this.skip = !this.skip);
    }

    /**
     * Returns the string representation of the player.
     *
     * @return The string describing the player.
     */
    @Override
    public String toString() {
        return "Player [strategic]\t| " + super.toString();
    }
}
