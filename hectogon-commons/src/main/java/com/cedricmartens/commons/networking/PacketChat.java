package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.chat.ChatType;

import java.io.*;

public class PacketChat extends Packet
{
    private String message;
    private int senderId;
    private ChatType chatType;

    public PacketChat()
    {
        super();
    }

    public PacketChat(String message, int senderId, ChatType chatType)
    {
        super();
        this.message = message;
        this.senderId = senderId;
        this.chatType = chatType;
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.message = dataInputStream.readUTF();
        this.senderId = dataInputStream.readInt();
        int chatType = dataInputStream.readInt();

        if(chatType < 0 || chatType >= ChatType.values().length)
            throw new InvalidPacketDataException(chatType + " is not a valid value for chatType");

        this.chatType = ChatType.values()[chatType];
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(message);
        dataOutputStream.writeInt(senderId);
        dataOutputStream.writeInt(chatType.ordinal());
    }

    public String getMessage() {
        return message;
    }

    public int getSenderId() {
        return senderId;
    }

    public ChatType getChatType() {
        return chatType;
    }

    @Override
    public String toString() {
        return "PacketChat{" +
                "message='" + message + '\'' +
                ", senderId=" + senderId +
                ", chatType=" + chatType +
                '}';
    }
}
