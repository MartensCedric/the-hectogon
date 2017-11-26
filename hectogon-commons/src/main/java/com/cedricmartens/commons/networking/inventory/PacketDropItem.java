package com.cedricmartens.commons.networking.inventory;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.storage.inventory.Item;

import java.io.*;

public class PacketDropItem extends Packet {

    private Item item;
    private int qty;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException
    {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int itemId = dataInputStream.readInt();
        if(itemId >= 0 && itemId < Item.values().length)
        {
            item = Item.values()[itemId];
        }else throw new InvalidPacketDataException();

        qty = dataInputStream.readInt();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(item.ordinal());
        dataOutputStream.writeInt(qty);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
