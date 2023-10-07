package capitaly.player;

import capitaly.tile.RealEstateTile;

public class GreedyPlayer extends Player {

    public GreedyPlayer(String name) {
        super(name);
    }

    /**
     * @param realEstateTile tile to be made the decision about
     * @return always true
     */
    @Override
    protected boolean shouldBuy(RealEstateTile realEstateTile) {
        return true;
    }
}
