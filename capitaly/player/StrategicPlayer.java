package capitaly.player;

import capitaly.tile.RealEstateTile;

public class StrategicPlayer extends Player {

    private boolean skip;

    public StrategicPlayer(String name) {
        super(name);
        this.skip = false;
    }

    @Override
    public final boolean wantsToBuy(RealEstateTile tile) {
        return this.canBuy(tile) && (this.skip = !this.skip);
    }
}
