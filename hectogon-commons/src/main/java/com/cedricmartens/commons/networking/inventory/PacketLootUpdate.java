package com.cedricmartens.commons.networking.inventory;

import com.cedricmartens.commons.networking.InvalidPacketDataException;

import java.io.*;

public class PacketLootUpdate extends PacketInventory
{
    private int lootId;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        super.readFrom(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        lootId = dataInputStream.readInt();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(lootId);
    }

    public int getLootId() {
        return lootId;
    }

    public void setLootId(int lootId) {
        this.lootId = lootId;
    }
}
