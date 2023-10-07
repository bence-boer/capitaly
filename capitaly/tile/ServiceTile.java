package capitaly.tile;

import capitaly.player.Player;

public final class ServiceTile extends Tile {
    private final int serviceFee;

    public ServiceTile(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public void enter(Player player) {
        player.pay(this.serviceFee);
    }
}
