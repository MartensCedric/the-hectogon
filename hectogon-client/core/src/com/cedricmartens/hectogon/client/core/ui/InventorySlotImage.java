package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cedricmartens.commons.storage.inventory.InventorySlot;

/**
 * Created by 1544256 on 2017-11-14.
 */
public class InventorySlotImage extends Image
{
    private InventorySlot inventorySlot;

    public InventorySlotImage(InventorySlot inventorySlot) {
        super();
        this.inventorySlot = inventorySlot;
    }

    public InventorySlot getInventorySlot() {
        return inventorySlot;
    }

    public void setInventorySlot(InventorySlot inventorySlot) {
        this.inventorySlot = inventorySlot;
    }
}
