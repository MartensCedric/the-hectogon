package com.cedricmartens.commons;

import com.cedricmartens.commons.networking.CustomSerializable;

import java.io.*;

public class Health implements CustomSerializable
{
    private float currentHealth;
    private float maxHealth;
    private float regenRate; //per second

    @Override
    public void readFrom(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        currentHealth = dataInputStream.readFloat();
        maxHealth = dataInputStream.readFloat();
        regenRate = dataInputStream.readFloat();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeFloat(currentHealth);
        dataOutputStream.writeFloat(maxHealth);
        dataOutputStream.writeFloat(regenRate);
    }
}
