package net.lexonje.library.inventory.type;

import net.lexonje.library.inventory.Canvas;
import net.lexonje.library.inventory.CanvasDimensions;
import net.lexonje.library.inventory.CloseHandler;

public class ChestCanvas extends Canvas {

    public ChestCanvas(String title, int rows) {
        this(title, rows, false);
    }

    public ChestCanvas(String title, int rows, boolean overDraw) {
        super(new CanvasDimensions(rows, 9), title);
        setOverDraw(overDraw);
    }

    public ChestCanvas(String title, int rows, CloseHandler closeHandler) {
        super(new CanvasDimensions(rows, 9), title, closeHandler);
    }

    public ChestCanvas(String title, int rows, boolean overDraw, CloseHandler closeHandler) {
        super(new CanvasDimensions(rows, 9), title, closeHandler);
        setOverDraw(overDraw);
    }
}
