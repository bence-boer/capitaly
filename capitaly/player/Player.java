package capitaly.player;

import capitaly.tile.LuckTile;
import capitaly.tile.RealEstateTile;
import capitaly.tile.ServiceTile;
import capitaly.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player {
    private static final int STARTING_CAPITAL = 10000;

    private final List<RealEstateTile> properties;
    private final String name;
    private int capital;
    private boolean bankrupt;

    protected Player(String name) {
        this.properties = new ArrayList<>();
        this.name = name;
        this.capital = Player.STARTING_CAPITAL;
        this.bankrupt = false;
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

    public final void stepOn(Tile tile) {
        if (tile instanceof RealEstateTile realEstateTile) {
            Player owner = realEstateTile.getOwner();
            if (this == owner || null == owner) {
                if (this.canBuy(realEstateTile) && this.shouldBuy(realEstateTile)) {
                    this.investIn(realEstateTile);
                }
            } else {
                int fee = realEstateTile.getPriceFor(this);
                if (fee > this.capital) {
                    this.bankrupt = true;
                    fee = this.capital;
                }

                this.pay(fee);
                owner.receive(fee);
            }
        } else if (tile instanceof ServiceTile serviceTile) {
            int fee = serviceTile.getServiceFee();
            if (fee > this.capital) {
                this.bankrupt = true;
                fee = this.capital;
            }
            this.pay(fee);
        } else if (tile instanceof LuckTile luckTile) {
            this.receive(luckTile.getReward());
        } else {
            throw new RuntimeException("Player behavior for tile " + tile.getClass() + " not implemented!");
        }
    }

    protected abstract boolean shouldBuy(RealEstateTile realEstateTile);

    private boolean canBuy(RealEstateTile realEstateTile) {
        return this.capital >= realEstateTile.getPriceFor(this);
    }

    private void investIn(RealEstateTile realEstateTile) {
        int price = realEstateTile.getPriceFor(this);

        if (realEstateTile.getOwner() == this) {
            this.pay(price);
            realEstateTile.upgrade();
        } else {
            this.pay(price);
            this.properties.add(realEstateTile);
            realEstateTile.purchase(this);
        }
    }

    private void pay(int amount) {
        this.capital -= amount;
    }

    private void receive(int amount) {
        this.capital += amount;
    }

    public void lose() {
        for (RealEstateTile property : this.properties) property.reset();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capital);
    }
}
