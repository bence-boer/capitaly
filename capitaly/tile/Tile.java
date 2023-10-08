package capitaly.tile;

import capitaly.player.Player;

import java.util.Objects;

/**
 * Represents a tile on the track.
 * The base class for all tiles.
 */
public abstract class Tile {
    /**
     * The number of tiles created.
     */
    private static int count;
    /**
     * The unique ID of the tile.
     */
    public final Integer UID;

    /**
     * Constructs a new Tile with a unique ID.
     */
    protected Tile(){
        this.UID = count++;
    }

    /**
     * Handles when a player enters the tile.
     * Behaviour implemented in subclasses.
     *
     * @param player The player that enters the tile.
     */
    public abstract void enter(Player player);

    /**
     * Compares the tile to another object.
     * Two tiles are equal if they have the same UID.
     *
     * @param o The object to compare to.
     * @return Whether the tile is equal to the object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;

        return UID.equals(tile.UID);
    }

    /**
     * Returns the hash code of the tile.
     *
     * @return The hash code of the tile.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.UID);
    }

    /**
     * Returns the string representation of the tile.
     *
     * @return The string describing the tile.
     */
    @Override
    public String toString() {
        return '[' + this.UID.toString() + ']';
    }
}
