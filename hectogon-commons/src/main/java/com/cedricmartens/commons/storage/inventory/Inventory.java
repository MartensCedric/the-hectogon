package com.cedricmartens.commons.storage.inventory;

import java.util.Iterator;

/**
 * Created by Cedric Martens on 05/03/17.
 */
public class Inventory implements Iterable<InventorySlot>
{
    protected int slotCount;
    protected InventorySlot[] slots;

    public Inventory(InventorySlot[] slots)
    {
        this.slotCount = slots.length;
        this.slots = slots;
    }

    public Inventory(int slotCount)
    {
        if(slotCount < 1)
            throw new IllegalArgumentException("Cannot create an inventory of " + slotCount + " slotCount");

        this.slotCount = slotCount;
        slots = new InventorySlot[slotCount];
        emptyInventory();
    }

    public void addItem(Item item, int amount)
    {
        if(amount < 1)
            throw new IllegalArgumentException();

        if(getCapacityForItem(item) < amount)
            throw new InventoryOperationException("The inventory cannot add " + amount + " " + item.name(), this);

        for(InventorySlot slot : slots)
        {
            if(slot.getItem() == item)
            {
                if(!slot.fullStack())
                {
                    int gapToFill = item.getStackMax() - slot.getQuantity();

                    if(gapToFill >= amount)
                    {
                        slot.add(amount);
                        amount = 0;
                        break;
                    }else {
                        slot.add(gapToFill);
                        amount -= gapToFill;
                    }
                }
            }
        }

        if(amount != 0)
        {
            int emptySlots = getSlotCount() + slots.length;

            for(int i = 0; i < emptySlots; i++)
            {
                if(amount <= item.getStackMax())
                {
                    InventorySlot slot = new InventorySlot(item);
                    slot.add(amount);
                    addItem(slot);
                    break;
                }else{
                    InventorySlot slot = new InventorySlot(item);
                    slot.add(item.getStackMax());
                    addItem(slot);
                    amount -= item.getStackMax();
                }
            }
        }
    }

    public void addItem(Item item)
    {
        addItem(item, 1);
    }

    public void replaceSlotAt(InventorySlot slot, int i)
    {
        if(i < 0  || i >= slots.length)
            throw new ArrayIndexOutOfBoundsException();

        slots[i] = slot;
    }

    public void removeItem(Item item, int amount)
    {
        if(amount < 1)
            throw new IllegalArgumentException();

        if(getQuantity(item) < amount)
            throw new InventoryOperationException("The inventory cannot remove " + amount + " " + item.name(), this);

        for(int i = 0; i < slots.length; i++)
        {
            if(slots[i].getItem() == item)
            {
                if(amount < slots[i]. getQuantity())
                {
                    slots[i].remove(amount);
                    amount = 0;
                    break;
                }else{
                    amount -= slots[i].getQuantity();
                    slots[i] = new InventorySlot(Item.empty_slot);
                    i--;
                }
            }
        }

        if(amount != 0)
            throw new InventoryOperationException("Inventory cannot be in this state", this);
    }

    public void removeItem(Item item)
    {
        removeItem(item, 1);
    }

    public void removeItemAt(int i)
    {
        if(i < 0  || i >= slots.length)
            throw new ArrayIndexOutOfBoundsException();

        if(slots[i].getItem() == Item.empty_slot)
            throw new InventoryOperationException("Cannot remove nothing", this);

        slots[i] = new InventorySlot(Item.empty_slot);
    }

    public boolean contains(Item item)
    {
        for(InventorySlot slot : slots)
        {
            if(slot.getItem() == item)
            {
                return true;
            }
        }
        return false;
    }

    public int getQuantity(Item item)
    {
        int count = 0;
        for(InventorySlot slot : slots)
        {
            if(slot.getItem() == item)
            {
                count += slot.getQuantity();
            }
        }
        return count;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public boolean hasEmptySlots()
    {
        for (InventorySlot slot : slots) {
            if (slot.getItem() == Item.empty_slot) {
                return true;
            }
        }
        return false;
    }

    public int emptySlotCount()
    {
        int c = 0;
        for (InventorySlot slot : slots) {
            if (slot.getItem() == Item.empty_slot)
                c++;
        }
        return c;
    }

    public int getCapacityForItem(Item item)
    {
        if(item.equals(Item.empty_slot))
            throw new InventoryOperationException("Cannot check capacity for nothing", this);

        int count = 0;
        int emptySlots = 0;
        for(InventorySlot slot : slots)
        {
            if(slot.getItem() == item)
            {
                count += item.getStackMax() - slot.getQuantity();
            }else if(slot.getItem() == Item.empty_slot)
            {
                emptySlots++;
            }
        }

        count += emptySlots * item.getStackMax();
        return count;
    }

    public int stackCount(Item item)
    {
        int count = 0;
        for(InventorySlot slot : slots)
        {
            if(slot.getItem() == item)
                count++;
        }
        return count;
    }

    private boolean hasEmptySlot()
    {
        for (InventorySlot invItem : slots) {
            if (invItem.getItem() == Item.empty_slot) {
                return true;
            }
        }
        return false;
    }

    private void addItem(InventorySlot slot)
    {
        for(int i = 0; i < getSlotCount(); i++)
        {
            if(slots[i].getItem() == Item.empty_slot)
            {
                slots[i] = slot;
                return;
            }
        }
        throw new InventoryOperationException("Cannot add if there's no more room in the inventory", this);
    }

    public boolean isEmpty()
    {
        for (InventorySlot slot : slots) {
            if (slot.getItem() != Item.empty_slot)
                return false;
        }
        return true;
    }

    private void emptyInventory()
    {
        for(int i = 0; i < slots.length; i++)
            slots[i] = new InventorySlot(Item.empty_slot);
    }

    @Override
    public String toString() {
        String str = "";

        for(int i = 0; i < slots.length; i++)
        {
            str += i + ". " + slots[i].getItem().name();

            if(slots[i].getQuantity() > 1)
            {
                str += " -> " + slots[i].getQuantity() + "/" + slots[i].getItem().getStackMax();
            }

            str += "\n";
        }

        return str;
    }

    @Override
    public Iterator<InventorySlot> iterator() {
        return new Iterator<InventorySlot>() {

            private int i = 0;
            @Override
            public boolean hasNext() {
                return i < Inventory.this.slots.length;
            }

            @Override
            public InventorySlot next() {
                return Inventory.this.slots[i++];
            }
        };
    }
}