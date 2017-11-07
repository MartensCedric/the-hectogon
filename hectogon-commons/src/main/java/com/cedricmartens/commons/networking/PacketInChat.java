package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.chat.ChatType;

import java.io.*;

public class PacketInChat extends Packet
{
    private String message;
    private int senderId;
    private ChatType chatType;

    public PacketInChat()
    {
        super();
    }

    public PacketInChat(String message, int senderId, ChatType chatType)
    {
        this.message = message;
        this.senderId = senderId;
        this.chatType = chatType;
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.message = dataInputStream.readUTF();
        this.senderId = dataInputStream.readInt();
        int chatType = dataInputStream.readInt();
        //TODO custom ex
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
}
