package com.cedricmartens.hectogon.client.core.ui.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;

public class InventoryUI extends InventoryTable
{
    private DropListener dropListener;

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

                    System.out.println("Drag");
                    if(is.getItem() != Item.empty_slot &&
                            selectedItem == null)
                    {
                        selectedItem = is.getItem();
                        System.out.println("DragStart " + selectedItem.getName());
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
                            System.out.println("Dragstop empty slot");
                            is.setItem(selectedItem);
                            selectedItem = null;
                        }else{
                            Item tempItem = is.getItem();
                            System.out.println("DragStop " + tempItem.getName());
                            is.setItem(selectedItem);
                            inventory.addItem(tempItem);
                            selectedItem = null;
                        }
                        init();
                        redraw();
                    }else{

                        if(dropListener != null)
                        {
                            dropListener.drop(selectedItem, 1);
                        }
                        selectedItem = null;
                    }
                }
            });
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
