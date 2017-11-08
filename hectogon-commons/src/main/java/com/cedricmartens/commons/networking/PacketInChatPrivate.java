package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.chat.ChatType;

import java.io.*;

public class PacketInChatPrivate extends PacketInChat {

    private int recipientId;

    public PacketInChatPrivate(String message, int senderId, int recipientId) {
        super(message, senderId, ChatType.PRIVATE);
        this.recipientId = recipientId;
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        super.readFrom(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        recipientId = dataInputStream.readInt();
        dataInputStream.close();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(recipientId);
        dataOutputStream.close();
    }

    public int getRecipientId() {
        return recipientId;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPacketInChatPrivate{" +
                "recipientId=" + recipientId +
                '}';
    }
}
