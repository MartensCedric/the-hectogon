package com.cedricmartens.hectogon.client.core.graphics.ui.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;

public class InventoryUI extends InventoryTable
{
    protected DropListener dropListener;
    protected Item selectedItem;
    protected int selectedAmount = 0;

    public InventoryUI(Inventory inventory)
    {
        setInventory(inventory);

        if(inventory.getSlotCount() != inventorySlotImages.length)
            throw new IllegalStateException();

        init();
        redraw();
    }

    public void setDropListener(DropListener dropListener)
    {
        this.dropListener = dropListener;
    }

    @Override
    public void init()
    {
        super.init();
        for(int i = 0; i < inventorySlotImages.length; i++)
        {
            final InventorySlot is = inventorySlotImages[i].getInventorySlot();
            inventorySlotImages[i].addListener(new DragListener()
            {
                @Override
                public void dragStart(InputEvent event, float x, float y, int pointer) {

                    if(is.getItem() != Item.empty_slot &&
                            selectedItem == null)
                    {
                        selectedItem = is.getItem();
                        selectedAmount = is.getQuantity();
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
                            is.add(selectedAmount);
                        }else
                        {
                            Item tempItem = is.getItem();
                            int tempAmount = is.getQuantity();
                            is.clear();
                            is.setItem(selectedItem);
                            is.add(selectedAmount);
                            inventory.addItem(tempItem, tempAmount);
                        }

                        clearSelectedItem();

                        init();
                        redraw();
                    }else
                    {
                        if(dropListener != null)
                            dropListener.drop(selectedItem, 1);

                        clearSelectedItem();
                    }
                }
            });
        }
    }

    public void clearSelectedItem()
    {
        selectedItem = null;
        selectedAmount = 0;
    }

    public Item getSelectedItem()
    {
        return selectedItem;
    }
    public int getSelectedAmount()
    {
        return selectedAmount;
    }
}