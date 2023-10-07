package capitaly.player;

import capitaly.tile.RealEstateTile;

public class CarefulPlayer extends Player {

    public CarefulPlayer(String name) {
        super(name);
    }

    @Override
    protected boolean shouldBuy(RealEstateTile realEstateTile) {
        return this.getCapital() >= realEstateTile.getPriceFor(this) / 2;
    }
}
