package com.cedricmartens.commons.storage.inventory;

/**
 * Created by Cedric Martens on 05/03/17.
 */
public class InventorySlot {

    private Item item;
    private int count;

    public InventorySlot(Item item)
    {
        this.item = item;
        this.count = 0;
    }

    public void add(int amount)
    {
        count += amount;
    }

    public void remove(int amount)
    {
        count -= amount;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return count;
    }

    public boolean fullStack()
    {
        return item.getStackMax() == getQuantity();
    }
}