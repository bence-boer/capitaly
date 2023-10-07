package capitaly.tile;

import capitaly.player.Player;

public final class LuckTile extends Tile {
    private final int reward;

    public LuckTile(int reward) {
        this.reward = reward;
    }

    @Override
    public void enter(Player player) {
        player.receive(this.reward);
    }

    @Override
    public String toString() {
        return super.toString() + " LuckTile ( +" + reward + " )";
    }
}
