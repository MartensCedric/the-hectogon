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
        this.inventory = new Inventory(inventorySize);
    }

    @Override
    public Iterator<InventorySlot> iterator() {
        return inventory.iterator();
    }
}
