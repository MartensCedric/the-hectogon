package com.cedricmartens.hectogon.client.core.graphics.ui.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

/**
 * Created by Cedric Martens on 2017-11-14.
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureUtil textureUtil = TextureUtil.getTextureUtil();
        if(this.inventorySlot.getQuantity() > 1)
            textureUtil.getFont().draw(batch, Integer.toString(inventorySlot.getQuantity()),getX() + getWidth() - 20, getY() + 15);

    }
}
