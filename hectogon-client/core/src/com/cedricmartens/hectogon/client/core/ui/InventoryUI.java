package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cedricmartens.commons.storage.inventory.Inventory;

public class InventoryUI extends Table
{
    private Image[] images;
    public InventoryUI(Inventory inventory)
    {
        images = new Image[inventory.getSlotCount()];
    }


}
