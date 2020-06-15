package net.lexonje.library.inventory.type;

import net.lexonje.library.inventory.Canvas;
import net.lexonje.library.inventory.CanvasDimensions;
import net.lexonje.library.inventory.CloseHandler;

public class HopperCanvas extends Canvas {

    public HopperCanvas(String title) {
        this(title, false, null);
    }

    public HopperCanvas(String title, boolean overDraw) {
        this(title, overDraw, null);
    }

    public HopperCanvas(String title, CloseHandler closeHandler) {
        this(title, false, closeHandler);
    }

    public HopperCanvas(String title, boolean overDraw, CloseHandler closeHandler) {
        super(new CanvasDimensions(1, 9), title, closeHandler);
        setOverDraw(overDraw);
    }
}
