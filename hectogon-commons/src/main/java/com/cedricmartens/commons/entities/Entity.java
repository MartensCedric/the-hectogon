package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.networking.CustomSerializable;
import com.cedricmartens.commons.networking.InvalidPacketDataException;

import java.io.*;

public abstract class Entity implements CustomSerializable, Identifiable
{
    public Entity(){this(0, 0);}
    public Entity(float x, float y)
    {
        setPosition(new Point(x, y));
    }
    public Entity(Point position) { setPosition(position); }
    private Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void move(float direction, float speed,  float deltaTime)
    {
        position.x += Math.cos(direction) * speed * deltaTime;
        position.y += Math.sin(direction) * speed * deltaTime;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeFloat(position.x);
        dataOutputStream.writeFloat(position.y);
        dataOutputStream.writeInt(getId());
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        position.x = dataInputStream.readFloat();
        position.y = dataInputStream.readFloat();
        setId(dataInputStream.readInt());
    }
}
