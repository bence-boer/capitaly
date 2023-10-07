package capitaly.tile;

public final class ServiceTile extends Tile {
    private final int serviceFee;

    public ServiceTile(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getServiceFee() {
        return serviceFee;
    }
}
