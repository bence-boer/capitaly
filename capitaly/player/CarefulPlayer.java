package capitaly.player;

import capitaly.tile.RealEstateTile;

/**
 * Represents a player that always acts carefully with their money.
 */
public class CarefulPlayer extends Player {

    /**
     * Constructs a new CarefulPlayer with the given name.
     *
     * @param name The name of the player.
     */
    public CarefulPlayer(String name) {
        super(name);
    }

    /**
     * Returns whether the player wants to buy the given tile.
     * A careful player wants to buy a tile if they have at least twice the price of the tile.
     *
     * @param tile The tile to buy.
     * @return Whether the player wants to buy the given tile.
     */
    @Override
    public final boolean wantsToBuy(RealEstateTile tile) {
        return this.getCapital() >= tile.getPriceFor(this) * 2;
    }

    /**
     * Returns the string representation of the player.
     *
     * @return The string describing the player.
     */
    @Override
    public String toString() {
        return "Player [careful]\t| " + super.toString();
    }
}
