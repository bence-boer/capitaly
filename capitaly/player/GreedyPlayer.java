package capitaly.player;

import capitaly.tile.RealEstateTile;

/**
 * Represents a player that always acts greedily with their money.
 */
public class GreedyPlayer extends Player {

    /**
     * Constructs a new GreedyPlayer with the given name.
     *
     * @param name The name of the player.
     */
    public GreedyPlayer(String name) {
        super(name);
    }

    /**
     * Returns whether the player wants to buy the given tile.
     * A greedy player always wants to buy a tile.
     *
     * @param tile tile to be made the decision about
     * @return always true
     */
    @Override
    public final boolean wantsToBuy(RealEstateTile tile) {
        return true;
    }

    /**
     * Returns the string representation of the player.
     *
     * @return The string describing the player.
     */
    @Override
    public String toString() {
        return "Player [greedy]\t\t| " + super.toString();
    }
}
