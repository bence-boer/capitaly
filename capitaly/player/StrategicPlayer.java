package capitaly.player;

import capitaly.tile.RealEstateTile;

public class StrategicPlayer extends Player {

    private boolean skip;

    public StrategicPlayer(String name) {
        super(name);
        this.skip = false;
    }

    @Override
    protected boolean shouldBuy(RealEstateTile realEstateTile) {
        return this.skip = !this.skip;
    }
}
