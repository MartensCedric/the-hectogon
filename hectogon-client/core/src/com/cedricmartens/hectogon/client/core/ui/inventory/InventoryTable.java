package com.cedricmartens.hectogon.client.core.ui.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

public class InventoryTable extends Table
{
    protected InventorySlotImage[] inventorySlotImages;
    protected Inventory inventory;
    protected Item selectedItem;
    protected final int ROWS = 3;
    protected final int COLUMNS = 4;

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
        inventorySlotImages = new InventorySlotImage[inventory.getSlotCount()];
        init();
        redraw();
    }

    protected void init()
    {
        int n = 0;
        for(final InventorySlot is : inventory)
        {
            inventorySlotImages[n] = new InventorySlotImage(is);
            n++;
        }

        clearChildren();

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLUMNS; j++)
            {
                add(inventorySlotImages[i * COLUMNS + j]).width(64).height(64).expand().fill();
            }
            row();
        }
    }


    protected void redraw()
    {
        TextureUtil textureUtil = TextureUtil.getTextureUtil();

        int n = 0;
        for(InventorySlot is : inventory)
        {
            inventorySlotImages[n].setInventorySlot(is);
            inventorySlotImages[n].setDebug(true);
            if(is.getItem() != Item.empty_slot)
            {
                Texture textureItem = textureUtil.getItemTexture(is.getItem());
                Drawable drawable = new TextureRegionDrawable(new TextureRegion(textureItem));
                inventorySlotImages[n].setDrawable(drawable);
            }else{
                inventorySlotImages[n].setDrawable(null);
            }
            n++;
        }
    }

}
