package capitaly.tile;

import capitaly.player.Player;

/**
 * Represents a tile of type "RealEstate" on a {@link capitaly.Track}
 */
public final class RealEstateTile extends Tile {

    /**
     * The original assign price for owning a {@link RealEstateTile}.
     * This is the cost incurred when a player lands on an unowned {@link RealEstateTile} and decides to buy it.
     *
     * @see #HOUSE_UPGRADE_COST
     */
    public static final int INITIAL_PURCHASE_COST = 1000;

    /**
     * The cost of adding a house to a {@link RealEstateTile}.
     * This price is applicable when a player chooses to upgrade an owned {@link RealEstateTile} by adding a house.
     *
     * @see #INITIAL_PURCHASE_COST
     */
    public static final int HOUSE_UPGRADE_COST = 4000;

    /**
     * The fee another player has to pay when landing on a {@link RealEstateTile} owned by someone else without a house.
     * This fee is incurred when a player lands on an owned {@link RealEstateTile} without a house upgrade.
     *
     * @see #INITIAL_PURCHASE_COST
     * @see #HOUSE_UPGRADE_COST
     */
    public static final int LANDING_FEE_NO_HOUSE = 500;

    /**
     * The fee another player has to pay when landing on a {@link RealEstateTile} owned by someone else with a house.
     * This fee is incurred when a player lands on an owned {@link RealEstateTile} that has a house upgrade.
     *
     * @see #INITIAL_PURCHASE_COST
     * @see #HOUSE_UPGRADE_COST
     * @see #LANDING_FEE_NO_HOUSE
     */
    public static final int LANDING_FEE_WITH_HOUSE = 2000;

    /**
     * The owner of the tile.
     */
    private Player owner;

    /**
     * Whether the tile has a house.
     */
    private boolean house;

    /**
     * Constructs a new RealEstateTile with no owner and no house.
     */
    public RealEstateTile() {
        this.owner = null;
        this.house = false;
    }

    /**
     * Handles the logic when a player enters this tile.
     *
     * @param player The player who enters the tile.
     */
    @Override
    public void enter(Player player) {
        int price = getPriceFor(player);

        if (player == this.owner && player.wantsToBuy(this)) {
            player.pay(price);
            this.upgrade();
        } else if (null == this.owner && player.wantsToBuy(this)) {
            player.pay(price);
            player.assign(this);
            this.assign(player);
        } else if (player != this.owner && null != this.owner) this.owner.receive(player.pay(price));
    }

    /**
     * Assigns the tile to a player.
     *
     * @param player The new owner of the tile.
     */
    public void assign(Player player) {
        this.owner = player;
    }

    /**
     * Upgrades the tile by adding a house.
     */
    public void upgrade() {
        this.house = true;
    }

    /**
     * Resets the tile to its initial state.
     */
    public void reset() {
        this.owner = null;
        this.house = false;
    }

    /**
     * Calculates the price for the given player to interact with this tile.
     *
     * @param player The player to calculate the price for.
     * @return The price the player has to pay.
     */
    public int getPriceFor(Player player) {
        if (this.owner == null) return INITIAL_PURCHASE_COST;
        if (this.owner == player) {
            if (this.house) return 0;
            else return HOUSE_UPGRADE_COST;
        } else {
            if (this.house) return LANDING_FEE_WITH_HOUSE;
            else return LANDING_FEE_NO_HOUSE;
        }
    }

    /**
     * Returns the string representation of the tile.
     *
     * @return The string describing the tile.
     */
    @Override
    public String toString() {
        return super.toString() + " RealEstateTile" + (house ? " + house" : "");
    }
}