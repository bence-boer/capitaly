package capitaly.tile;

import capitaly.player.Player;

public final class ServiceTile extends Tile {
    private final int fee;

    public ServiceTile(int fee) {
        this.fee = fee;
    }

    @Override
    public void enter(Player player) {
        player.pay(this.fee);
    }

    @Override
    public String toString() {
        return super.toString() + " ServiceTile ( -" + fee + " )";
    }
}
