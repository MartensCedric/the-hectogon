package com.cedricmartens.commons.storage.inventory;

/**
 * Created by Cedric Martens on 05/03/17.
 */
public class InventoryOperationException extends RuntimeException
{
    public InventoryOperationException(String message, Inventory inventory)
    {
        super(message);
        System.err.print(inventory.toString());
    }

    public InventoryOperationException() {
        super();
    }
}