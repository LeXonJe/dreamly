package net.lexonje.library.inventory.render;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * ItemRenderer for items that don't change.
 */
public class StaticItemRenderer implements ItemRenderer {

    private ItemStack item;

    public StaticItemRenderer(ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack render(Player player) {
        return item == null ? null : new ItemStack(item);
    }
}
