package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;

public class InventoryUI extends Table
{
    private InventorySlotImage[] inventorySlotImages;
    private Inventory inventory;
    private Item selectedItem;
    private final int ROWS = 3;
    private final int COLUMNS = 4;

    public InventoryUI(Inventory inventory)
    {
        this.inventory = inventory;
        inventorySlotImages = new InventorySlotImage[inventory.getSlotCount()];

        if(inventory.getSlotCount() != inventorySlotImages.length)
            throw new IllegalStateException();

        int n = 0;
        for(InventorySlot is : inventory)
        {
            inventorySlotImages[n] = new InventorySlotImage(is);
            //inventorySlotImages[n].setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
            n++;
        }

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLUMNS; j++)
            {
                add(inventorySlotImages[i * COLUMNS + j]);
            }
            row();
        }
    }
}
