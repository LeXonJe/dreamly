package net.lexonje.library.inventory;

import org.bukkit.event.inventory.InventoryType;

import java.util.Objects;

/**
 * Contains some information about the size of a canvas.
 */
public class CanvasDimensions {

    private final int rows;
    private final int columns;
    private final int extra;
    private final int totalSlots;

    public CanvasDimensions(int rows, int columns) {
        this(rows, columns, 0);
    }

    public CanvasDimensions(int rows, int columns, int extra) {
        this.rows = rows;
        this.columns = columns;
        this.extra = extra;
        this.totalSlots = rows * columns + extra;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getExtraSlots() {
        return extra;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanvasDimensions that = (CanvasDimensions) o;
        return rows == that.rows &&
                columns == that.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }
}
