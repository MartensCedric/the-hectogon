package com.cedricmartens.hectogon.client.core.graphics.ui.inventory;

import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.Item;

public interface InventoryService
{
    void transfer(Item item, int qty, Inventory inventorySender, Inventory inventoryReceiver);
}
