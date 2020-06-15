package net.lexonje.library.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Listens for the backend stuff. //TODO: Better description. xd
 */
public class CanvasListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getClickedInventory().getHolder();
        if (holder instanceof CanvasHolder) { //Checks if the inventory is a canvas
            if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && shiftClickAllow(holder.getInventory().getType())) { //Checks for shiftClickAllowed lol
                Canvas canvas = ((CanvasHolder) holder).getCanvas();
                canvas.getSlot(event.getSlot()).getHandler().ifPresent(slotHandler -> slotHandler.clicked(event));
            }
        }
    }

    /**
     * On some inventories is seems to cause a StackOverflowError. So this returns whether its should be allowed or not.
     */
    private boolean shiftClickAllow(InventoryType type) {
        switch (type) {
            case DROPPER:
            case DISPENSER:
            case WORKBENCH:
            case HOPPER:
                return false;
        }
        return true;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof CanvasHolder) {
            Canvas canvas = ((CanvasHolder) holder).getCanvas();
            for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
                int index = entry.getKey();
                canvas.getSlot(index).getHandler().ifPresent(slotHandler -> slotHandler.dragged(event));
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof CanvasHolder) {
            ((CanvasHolder) holder).getCanvas().closed((CanvasHolder) holder);
        }
    }
}
