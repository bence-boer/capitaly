package capitaly.tile;

import capitaly.player.Player;

import java.util.Objects;

public abstract class Tile {
    private static int count;
    public final Integer ID;

    protected Tile(){
        this.ID = count++;
    }
    public abstract void enter(Player player);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;

        return ID.equals(tile.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ID);
    }

    @Override
    public String toString() {
        return '[' + this.ID.toString() + ']';
    }
}
