package com.cedricmartens.hectogon.client.core.ui.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
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
        setInventory(inventory);

        if(inventory.getSlotCount() != inventorySlotImages.length)
            throw new IllegalStateException();

        init();
        redraw();
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
        inventorySlotImages = new InventorySlotImage[inventory.getSlotCount()];
        init();
        redraw();
    }

    public void init()
    {
        int n = 0;
        for(final InventorySlot is : inventory)
        {
            inventorySlotImages[n] = new InventorySlotImage(is);
            inventorySlotImages[n].addListener(new DragListener(){

                @Override
                public void dragStart(InputEvent event, float x, float y, int pointer) {

                    if(is.getItem() != Item.empty_slot &&
                            selectedItem == null)
                    {
                        selectedItem = is.getItem();
                        is.clear();
                        redraw();
                    }
                }



                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {

                    if(selectedItem == null) return;
                    
                    Vector2 pos = event.getTarget().localToParentCoordinates(new Vector2(x, y));
                    Actor actor = hit(pos.x, pos.y, true);
                    InventorySlotImage isi = (InventorySlotImage) actor;
                    if(isi != null)
                    {
                        InventorySlot is = isi.getInventorySlot();
                        if(is.getItem() == Item.empty_slot)
                        {
                            is.setItem(selectedItem);
                            selectedItem = null;
                        }else{
                            Item tempItem = is.getItem();
                            is.setItem(selectedItem);
                            inventory.addItem(tempItem);
                            selectedItem = null;
                        }
                        redraw();
                    }else{
                        selectedItem = null;
                        System.out.println("Item dropped");
                    }
                }
            });
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

    public void redraw()
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

    public void clearSelectedItem()
    {
        selectedItem = null;
    }

    public Item getSelectedItem()
    {
        return selectedItem;
    }
}
