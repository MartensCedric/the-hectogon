package com.cedricmartens.commons.entities.combat;

import com.cedricmartens.commons.entities.Entity;
import com.cedricmartens.commons.networking.InvalidPacketDataException;

import java.io.*;

public abstract class Projectile extends Entity{

    private int projectileId;
    private int senderId;
    protected float speed;
    protected float directionRad;
    protected float ttl;


    @Override
    public int getId() {
        return projectileId;
    }

    @Override
    public void setId(int id) {
        this.projectileId = projectileId;
    }

    public int getSenderId() { return senderId; }

    public void setSenderId(int id){
        this.senderId = id;
    }

    public void update(float deltaTime)
    {
        ttl -= deltaTime;
        getPosition().x += Math.cos(directionRad) * speed * deltaTime;
        getPosition().y += Math.sin(directionRad) * speed * deltaTime;
    }

    public float getTtl() {
        return ttl;
    }

    public void setTtl(float ttl) {
        this.ttl = ttl;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getDirection() { return directionRad; }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(projectileId);
        dataOutputStream.writeInt(senderId);
        dataOutputStream.writeFloat(speed);
        dataOutputStream.writeFloat(directionRad);
        dataOutputStream.writeFloat(ttl);
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        super.readFrom(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        projectileId = dataInputStream.readInt();
        senderId = dataInputStream.readInt();
        speed = dataInputStream.readFloat();
        directionRad = dataInputStream.readFloat();
        ttl = dataInputStream.readFloat();
    }
}
