package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

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

        refresh();
    }

    public void refresh()
    {
        clearChildren();
        TextureUtil textureUtil = TextureUtil.getTextureUtil();
        int n = 0;
        for(final InventorySlot is : inventory)
        {
            inventorySlotImages[n] = new InventorySlotImage(is);
            inventorySlotImages[n].setDebug(true);
            if(is.getItem() != Item.empty_slot)
            {
                Texture textureItem = textureUtil.getItemTexture(is.getItem());
                Drawable drawable = new TextureRegionDrawable(new TextureRegion(textureItem));
                inventorySlotImages[n].setDrawable(drawable);
            }

            inventorySlotImages[n].addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(selectedItem == null)
                    {
                        if(is.getItem() != Item.empty_slot)
                        {
                            selectedItem = is.getItem();
                            is.clear();
                        }
                    }else
                    {
                        if(is.getItem() == Item.empty_slot)
                        {
                            is.setItem(selectedItem);
                            selectedItem = null;
                        }else{
                            Item tempItem = selectedItem;
                            selectedItem = is.getItem();
                            is.setItem(tempItem);
                        }
                    }
                    refresh();
                }
            });

            n++;
        }

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLUMNS; j++)
            {
                add(inventorySlotImages[i * COLUMNS + j]).width(64).height(64).expand().fill();
            }
            row();
        }
    }

    public void clearSelectedItem()
    {
        selectedItem = null;
    }

    public Item getSelectedItem()
    {
        return selectedItem;
    }
}
