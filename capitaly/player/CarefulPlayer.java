package capitaly.player;

import capitaly.tile.RealEstateTile;

public class CarefulPlayer extends Player {

    public CarefulPlayer(String name) {
        super(name);
    }

    @Override
    public final boolean wantsToBuy(RealEstateTile tile) {
        return this.getCapital() >= tile.getPriceFor(this) * 2;
    }
}
