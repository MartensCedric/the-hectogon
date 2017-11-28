package com.cedricmartens.commons.networking.actions;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

public class PacketPositionCorrection extends Packet
{
    private int userId;
    private long time;
    private Point position;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        userId = dataInputStream.readInt();
        float x = dataInputStream.readFloat();
        float y = dataInputStream.readFloat();
        this.position = new Point(x, y);
        time = dataInputStream.readLong();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(userId);
        dataOutputStream.writeFloat(position.x);
        dataOutputStream.writeFloat(position.y);
        dataOutputStream.writeLong(System.currentTimeMillis());
        //TODO verify that System.currentTimeMillis has no delay between systems
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTime() {
        return time;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
