package net.lexonje.library.inventory.slot;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public interface SlotHandler {

    void clicked(InventoryClickEvent event);
    void dragged(InventoryDragEvent event); //TODO What does dragged do?
}
