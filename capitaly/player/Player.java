package capitaly.player;

import capitaly.tile.RealEstateTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player {
    private static final int STARTING_CAPITAL = 10000;
    private static int count;

    private final Integer ID;
    private final String name;
    private final List<RealEstateTile> properties;
    private int capital;
    private boolean bankrupt;

    protected Player(String name) {
        this.ID = count++;
        this.name = name;
        this.properties = new ArrayList<>();
        this.capital = Player.STARTING_CAPITAL;
        this.bankrupt = false;
    }

    public abstract boolean wantsToBuy(RealEstateTile tile);

    public final boolean canBuy(RealEstateTile tile) {
        return this.capital >= tile.getPriceFor(this);
    }

    public final void assign(RealEstateTile tile) {
        this.properties.add(tile);
    }

    public final int pay(int amount) {
        if (amount > this.capital) {
            this.bankrupt = true;
            amount = this.capital;
        }

        this.capital -= amount;
        return amount;
    }

    public final void receive(int amount) {
        this.capital += amount;
    }

    public final void lose() {
        for (RealEstateTile property : this.properties) property.reset();
    }

    public String getName() {
        return name;
    }

    public int getCapital() {
        return capital;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ID, this.name);
    }

    @Override
    public String toString() {
        return name + '\t' +
            "\tproperties: " + properties +
            "\tcapital: " + capital +
            "\tbankrupt: " + (bankrupt ? "yes" : "no");
    }
}
