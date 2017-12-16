package com.cedricmartens.commons.storage;

import com.cedricmartens.commons.entities.Entity;
import com.cedricmartens.commons.storage.inventory.Inventory;

public class Lootbag extends Entity
{
    private Inventory inventory;
    private int lootbagId;

    public Lootbag(float x, float y, Inventory inventory)
    {
        super(x, y);
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getId() {
        return lootbagId;
    }

    @Override
    public void setId(int id) {
        this.lootbagId = id;
    }
}
