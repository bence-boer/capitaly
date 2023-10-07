package capitaly.player;

import capitaly.tile.RealEstateTile;

public class GreedyPlayer extends Player {

    public GreedyPlayer(String name) {
        super(name);
    }

    /**
     * @param tile tile to be made the decision about
     * @return always true
     */
    @Override
    public final boolean wantsToBuy(RealEstateTile tile) {
        return true;
    }

    @Override
    public String toString() {
        return "Player [greedy]\t\t| " + super.toString();
    }
}
