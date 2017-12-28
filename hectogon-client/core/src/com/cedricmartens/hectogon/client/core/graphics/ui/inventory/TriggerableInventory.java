package com.cedricmartens.hectogon.client.core.graphics.ui.inventory;

import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;

public class TriggerableInventory extends Inventory {

    private OnChangeListener onChangeListener;

    protected static InventorySlot[] inventoryToSlots(Inventory inventory)
    {
        InventorySlot[] slots = new InventorySlot[inventory.getSlotCount()];
        int n = 0;
        for(InventorySlot inventorySlots : inventory)
        {
            slots[n] = inventorySlots;
            n++;
        }
        return slots;
    }
    public TriggerableInventory(Inventory inventory)
    {
        this(TriggerableInventory.inventoryToSlots(inventory));
    }

    public TriggerableInventory(InventorySlot[] slots) {
        super(slots);
    }

    public TriggerableInventory(int slotCount)
    {
        super(slotCount);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener)
    {
        this.onChangeListener = onChangeListener;
    }

    private void trigger()
    {
        if(onChangeListener != null)
        {
            onChangeListener.onChange();
        }
    }

    @Override
    public void addItem(Item item, int amount) {
        super.addItem(item, amount);
        trigger();
    }

    @Override
    public void replaceSlotAt(InventorySlot slot, int i) {
        super.replaceSlotAt(slot, i);
        trigger();
    }

    @Override
    public void removeItem(Item item, int amount) {
        super.removeItem(item, amount);
        trigger();
    }

    @Override
    public void removeItemAt(int i) {
        super.removeItemAt(i);
        trigger();
    }
}
