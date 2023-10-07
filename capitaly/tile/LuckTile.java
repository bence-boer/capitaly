package capitaly.tile;

public final class LuckTile extends Tile {
    private final int reward;

    public LuckTile(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }
}
