package com.cedricmartens.commons.networking.inventory;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.networking.InvalidPacketDataException;

import java.io.*;

public class PacketLoot extends PacketInventory
{
    private Point p;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        super.readFrom(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        float x = dataInputStream.readFloat();
        float y = dataInputStream.readFloat();
        p = new Point(x, y);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeFloat(p.x);
        dataOutputStream.writeFloat(p.y);
    }

    public Point getPoint() {
        return p;
    }

    public void setPoint(Point p) {
        this.p = p;
    }
}
