package capitaly.player;

import capitaly.tile.RealEstateTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player in the game.
 * The base class for all players.
 */
public abstract class Player {
    /**
     * The starting capital of the players.
     */
    private static final int STARTING_CAPITAL = 10000;

    /**
     * The number of players created.
     */
    private static int count;

    /**
     * The unique ID of the player.
     */
    public final Integer UID;

    /**
     * The name of the player.
     */
    private final String name;

    /**
     * The properties bought by the player.
     */
    private final List<RealEstateTile> properties;

    /**
     * The capital of the player.
     */
    private int capital;

    /**
     * Whether the player is bankrupt.
     */
    private boolean bankrupt;

    /**
     * Constructs a new Player with the given name, a unique ID, and the starting capital.
     *
     * @param name The name of the player.
     */
    protected Player(String name) {
        this.UID = count++;
        this.name = name;
        this.properties = new ArrayList<>();
        this.capital = Player.STARTING_CAPITAL;
        this.bankrupt = false;
    }

    /**
     * Returns whether the player wants to buy the given tile.
     * The decision is made by the subclasses.
     *
     * @param tile The tile to buy.
     * @return Whether the player wants to buy the given tile.
     */
    public abstract boolean wantsToBuy(RealEstateTile tile);

    /**
     * Returns whether the player can buy the given tile.
     * The player can buy the tile if they have enough money.
     *
     * @param tile The tile to buy.
     * @return Whether the player can buy the given tile.
     */
    public final boolean canBuy(RealEstateTile tile) {
        return this.capital >= tile.getPriceFor(this);
    }

    /**
     * Handles the logic when a player buys a tile.
     */
    public final void assign(RealEstateTile tile) {
        this.properties.add(tile);
    }

    /**
     * Handles the logic when a player needs to pay a certain amount of money.
     * If the player does not have enough money, they pay all their money and go bankrupt.
     */
    public final int pay(int amount) {
        if (amount > this.capital) {
            this.bankrupt = true;
            amount = this.capital;
        }

        this.capital -= amount;
        return amount;
    }

    /**
     * Handles the logic when a player receives a certain amount of money.
     */
    public final void receive(int amount) {
        this.capital += amount;
    }

    /**
     * Handles the logic when a player loses the game.
     * The player loses all their properties.
     */
    public final void lose() {
        for (RealEstateTile property : this.properties) property.reset();
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the capital of the player.
     *
     * @return The capital of the player.
     */
    public int getCapital() {
        return capital;
    }

    /**
     * Returns whether the player is bankrupt.
     *
     * @return Whether the player is bankrupt.
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Returns the hash code of the player.
     * The hash code is based on the ID and the name of the player.
     *
     * @return The hash code of the player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.UID, this.name);
    }

    /**
     * Returns the string representation of the player.
     *
     * @return The string describing the player.
     */
    @Override
    public String toString() {
        return name + '\t' +
            "\tproperties: " + properties +
            "\tcapital: " + capital +
            "\tbankrupt: " + (bankrupt ? "yes" : "no");
    }
}
