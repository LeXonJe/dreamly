package net.lexonje.library.inventory;

import net.lexonje.library.inventory.slot.Slot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents an interactive inventory for Players.
 */
public abstract class Canvas {

    private String title;
    private CanvasDimensions canvasDimensions;
    private Slot[] slots;

    private ArrayList<CanvasHolder> canvasHolders;
    private Optional<CloseHandler> closeHandler;
    private boolean overDraw; //Check, if a inventory can be overwritten by this canvas.

    public Canvas(CanvasDimensions canvasDimensions) {
        this(canvasDimensions, "Inventory", null);
    }

    public Canvas(CanvasDimensions canvasDimensions, String title) {
        this(canvasDimensions, title, null);
    }

    public Canvas(CanvasDimensions canvasDimensions, String title, CloseHandler closeHandler) {
        this.canvasDimensions = canvasDimensions;
        this.title = title;
        this.closeHandler = Optional.ofNullable(closeHandler);
        this.canvasHolders = new ArrayList<>();
        initSlots();
    }

    /**
     * Fills the slots of the canvas.
     */
    private void initSlots() {
        slots = new Slot[canvasDimensions.getTotalSlots()];
        for (int i = 0; i < slots.length; i++)
            slots[i] = new Slot(this, i);
    }

    /**
     * Creates an inventory instance for this canvas.
     * @param holder the canvasholder for the canvas
     * @return the inventory instance
     */
    private Inventory makeInventory(CanvasHolder holder) {
        return Bukkit.createInventory(holder, canvasDimensions.getTotalSlots(), title);
    }

    /**
     * Updates all slots in an inventory instance.
     * @param inventory the inventory that is being updated
     * @param player the viewer
     */
    private void updateInventory(Inventory inventory, Player player) {
        for (int i = 0; i < slots.length; i++)
            inventory.setItem(i, slots[i].getItem(player));
    }

    public Slot getSlot(int index) {
        return slots[index];
    }

    /**
     * Gets the slot by row and column. NOTE: Both inputs should start at 1.
     * @param row the row where the slot belongs
     * @param column the column where to slot belongs
     * @return the slot
     */
    public Slot getSlot(int row, int column) {
        int rowIndex = (row - 1) * canvasDimensions.getColumns(); //Represents the first index in a row
        int totalIndex = rowIndex + column - 1;  //Represents the total index in an inventory (-1 because Arrays start at 0)
        return slots[totalIndex];
    }

    public Slot[] getSlots() {
        return slots;
    }

    public Optional<CloseHandler> getCloseHandler() {
        return closeHandler;
    }

    public void setOverDraw(boolean overDraw) {
        this.overDraw = overDraw;
    }

    public boolean isOverDraw() {
        return overDraw;
    }

    /**
     * Opens a inventory instance of this canvas for a specific player.
     * @param player The viewer
     */
    public void open(Player player) {
        InventoryHolder invHolder = player.getOpenInventory().getTopInventory().getHolder();
        if (invHolder instanceof CanvasHolder) {
            CanvasHolder holder = (CanvasHolder) invHolder;
            Canvas canvas = holder.getCanvas();
            if (canvas != this) {
                Inventory inventory;
                if (overDraw && canvas.canvasDimensions.equals(canvasDimensions)) { //If the canvas can be overwritten by this
                    canvas.closed(holder);
                    inventory = holder.getInventory();
                } else { //Else create a new inventory
                    canvas.closed(holder);
                    inventory = makeInventory(holder);
                    holder.setInventory(inventory);
                    player.openInventory(inventory);
                }
                updateInventory(inventory, player);
                holder.setCanvas(this);
                canvasHolders.add(holder);
            }
        } else {
            CanvasHolder holder = new CanvasHolder(this, player);
            Inventory inventory = makeInventory(holder);
            updateInventory(inventory, player);
            holder.setInventory(inventory);
            player.openInventory(inventory);
            canvasHolders.add(holder);
        }
    }

    /**
     * Redraws or updates the current open inventory.
     * @param player The viewer
     */
    public void redraw(Player player) {
        InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
        if (canvasHolders.contains(holder)) {
            updateInventory(holder.getInventory(), player);
        }
    }

    /**
     * Closes the canvas for a player that is currently viewing it.
     * @param player The viewer
     */
    public void close(Player player) {
        InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
        if (holder instanceof CanvasHolder && canvasHolders.contains(holder)) {
            closed((CanvasHolder) holder);
            player.closeInventory();
        }
    }

    /**
     * Will be triggered if the canvas was closed by something.
     * @param holder The inventory holder
     */
    protected void closed(CanvasHolder holder) {
        holder.getCanvas().getCloseHandler().ifPresent(handler -> handler.onClose(holder.getPlayer(), this));
        canvasHolders.remove(holder);
    }

    /**
     * Clears all items in this canvas. Sets the ItemRenderer of all slots to null.
     */
    public void clear() {
        for (Slot slot : slots)
            slot.setItemRenderer(null);
    }

    /**
     * Clears a item in a specific location of a canvas.
     * @param index
     */
    public void clear(int index) {
        slots[index].setItemRenderer(null);
    }
}
