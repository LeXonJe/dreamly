package net.lexonje.library.inventory.slot;

import net.lexonje.library.inventory.Canvas;
import net.lexonje.library.inventory.render.ItemRenderer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Represents a slot in a canvas. Contains all information and functions about it.
 */
public class Slot {

    private final Canvas canvas;
    private final int index;
    private ItemRenderer renderer;
    private Optional<SlotHandler> handler;

    public Slot(Canvas canvas, int index) {
        this(canvas, index, null, null);
    }

    public Slot(Canvas canvas, int index, ItemRenderer itemRenderer) {
        this(canvas, index, itemRenderer, null);
    }

    public Slot(Canvas canvas, int index, ItemRenderer itemRenderer, SlotHandler slotHandler) {
        this.canvas = canvas;
        this.index = index;
        this.renderer = itemRenderer;
        this.handler = Optional.ofNullable(slotHandler);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getIndex() {
        return index;
    }

    public void setItemRenderer(ItemRenderer itemRenderer) {
        this.renderer = itemRenderer;
    }

    public ItemStack getItem(Player player) {
        return renderer == null ? null : renderer.render(player); //Check for avoiding NullPointerException.
    }

    public void setHandler(SlotHandler handler) {
        this.handler = Optional.ofNullable(handler);
    }

    public Optional<SlotHandler> getHandler() {
        return handler;
    }
}
