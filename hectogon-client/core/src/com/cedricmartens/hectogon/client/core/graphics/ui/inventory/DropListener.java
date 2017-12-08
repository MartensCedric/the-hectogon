package com.cedricmartens.hectogon.client.core.graphics.ui.inventory;

import com.cedricmartens.commons.storage.inventory.Item;

public interface DropListener
{
    void drop(Item item,int qty);
}
