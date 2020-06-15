package net.lexonje.library.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

/**
 * Needed for checking if a inventory belongs to a canvas.
 */
public class CanvasHolder implements InventoryHolder {

    private Canvas canvas;
    private Player player;
    private Inventory inventory;

    public CanvasHolder(Canvas canvas, Player player) {
        this(canvas, player, null);
    }

    public CanvasHolder(Canvas canvas, Player player, Inventory inventory) {
        this.canvas = canvas;
        this.player = player;
        this.inventory = inventory;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCanvas(Canvas canvas) {
        Objects.requireNonNull(canvas);
        this.canvas = canvas;
    }

    public void setInventory(Inventory inventory) {
        Objects.requireNonNull(inventory);
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanvasHolder that = (CanvasHolder) o;
        return Objects.equals(canvas, that.canvas) &&
                Objects.equals(player, that.player) &&
                Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canvas, player, inventory);
    }
}
