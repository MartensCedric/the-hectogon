package com.cedricmartens.commons.networking;


import java.io.*;

public abstract class Packet
{
    public static final int NO_ID = -1;

    public Packet(){}

    public abstract void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException;
    public abstract void writeTo(OutputStream outputStream) throws IOException;

    public static void writeHeader(Class<? extends Packet> packet, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        int type = PacketType.idFromType(packet);
        dataOutputStream.writeInt(type);
        dataOutputStream.close();
    }

    public static Packet readHeader(InputStream inputStream) throws IOException, IllegalAccessException, InstantiationException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int type = dataInputStream.readInt();
        dataInputStream.close();
        return PacketType.getById(type).newInstance();
    }
}
