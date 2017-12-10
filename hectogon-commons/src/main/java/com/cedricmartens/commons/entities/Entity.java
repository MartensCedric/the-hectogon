package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.networking.CustomSerializable;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.util.Vector2;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Entity implements CustomSerializable
{
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
        dataOutputStream.writeUTF(getClass().getName());
        dataOutputStream.writeFloat(position.x);
        dataOutputStream.writeFloat(position.y);
    }
}
