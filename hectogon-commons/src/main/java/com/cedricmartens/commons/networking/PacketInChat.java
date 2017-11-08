package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.chat.ChatType;

import java.io.*;

public class PacketInChat extends Packet
{
    private String message;
    private int senderId;
    private ChatType chatType;
    private int matchId;

    public PacketInChat()
    {
        super();
    }

    public PacketInChat(String message, int senderId, int matchId, ChatType chatType)
    {
        this.message = message;
        this.senderId = senderId;
        this.chatType = chatType;
        this.matchId = matchId;
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.message = dataInputStream.readUTF();
        this.senderId = dataInputStream.readInt();
        int chatType = dataInputStream.readInt();

        if(chatType < 0 || chatType >= ChatType.values().length)
        {
            dataInputStream.close();
            throw new InvalidPacketDataException(chatType + " is not a valid value for chatType");
        }

        this.chatType = ChatType.values()[chatType];
        this.matchId = dataInputStream.readInt();
        dataInputStream.close();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(message);
        dataOutputStream.writeInt(senderId);
        dataOutputStream.writeInt(chatType.ordinal());
        dataOutputStream.writeInt(matchId);
        dataOutputStream.close();
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
        return "PacketInChat{" +
                "message='" + message + '\'' +
                ", senderId=" + senderId +
                ", chatType=" + chatType +
                '}';
    }
}
