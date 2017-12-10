package com.cedricmartens.commons.storage;

import com.cedricmartens.commons.entities.SolidEntity;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;

import java.util.Iterator;

public class Chest extends SolidEntity implements Iterable<InventorySlot>
{
    private Inventory inventory;

    public Chest(int inventorySize)
    {
        super(0, 0);
        this.inventory = new Inventory(inventorySize);
    }

    @Override
    public Iterator<InventorySlot> iterator() {
        return inventory.iterator();
    }

    @Override
    public int getBottomLimit() {
        return -16;
    }

    @Override
    public int getUpperLimit() {
        return 40;
    }

    @Override
    public int getLeftLimit() {
        return 4;
    }

    @Override
    public int getRightLimit() {
        return 59;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }
}
