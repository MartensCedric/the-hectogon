package com.cedricmartens.commons;

import com.cedricmartens.commons.networking.CustomSerializable;
import com.cedricmartens.commons.networking.InvalidPacketDataException;

import java.io.*;

public class Health implements CustomSerializable
{
    private float currentHealth;
    private float maxHealth;
    private float regenRate; //per second

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeFloat(currentHealth);
        dataOutputStream.writeFloat(maxHealth);
        dataOutputStream.writeFloat(regenRate);
    }

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        currentHealth = dataInputStream.readFloat();
        maxHealth = dataInputStream.readFloat();
        regenRate = dataInputStream.readFloat();
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getRegenRate() {
        return regenRate;
    }

    public void setRegenRate(float regenRate) {
        this.regenRate = regenRate;
    }
}
