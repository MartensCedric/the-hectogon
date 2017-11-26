package com.cedricmartens.commons.networking.inventory;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;

import java.io.*;

public class PacketInventory extends Packet {

    private Inventory inventory;
    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int inventorySize = dataInputStream.readInt();
        this.inventory = new Inventory(inventorySize);
        int emptyCount = dataInputStream.readInt();

        for(int i = 0; i < inventorySize - emptyCount; i++)
        {
            int itemId = dataInputStream.readInt();
            if(itemId >= 0 && itemId < Item.values().length)
            {
                Item item = Item.values()[itemId];
                int qty = dataInputStream.readInt();

                this.inventory.addItem(item, qty);
            }else throw new InvalidPacketDataException();
        }
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(inventory.getSlotCount());
        dataOutputStream.writeInt(inventory.emptySlotCount());
        for(InventorySlot is : inventory)
        {
            if(is.getItem() != Item.empty_slot)
            {
                dataOutputStream.writeInt(is.getItem().ordinal());
                dataOutputStream.writeInt(is.getQuantity());
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
