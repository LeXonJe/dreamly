package net.lexonje.library.inventory.render;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Renders a item for a viewer/player.
 */
public interface ItemRenderer {

    ItemStack render(Player player);
}
