package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.Health;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.entities.Entity;
import com.cedricmartens.commons.entities.animal.Animal;
import com.cedricmartens.commons.entities.animal.AnimalState;
import com.cedricmartens.commons.util.Vector2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Serializer
{
    public static <T> T readEntity(InputStream inputStream) throws InvalidPacketDataException, IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String className = dataInputStream.readUTF();
        try {
            Class<T> classType = (Class<T>) Class.forName(className);
            Point position = new Point(dataInputStream.readFloat(),
                    dataInputStream.readFloat());
            Constructor<T> ctor = classType.getConstructor(Point.class);
            return ctor.newInstance(position);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new InvalidPacketDataException();
    }

    public static Health readHealth(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Health health = new Health();
        health.setCurrentHealth(dataInputStream.readFloat());
        health.setMaxHealth(dataInputStream.readFloat());
        health.setRegenRate(dataInputStream.readFloat());
        return health;
    }

    public static Vector2 readVector2(InputStream inputStream) throws IOException
    {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        return new Vector2(dataInputStream.readFloat(), dataInputStream.readFloat());
    }

    public static Animal readAnimal(InputStream inputStream) throws IOException
    {
        Health health = readHealth(inputStream);
        Vector2 direction = readVector2(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        boolean targetExists = dataInputStream.readBoolean();
        Entity target = null;
        if(targetExists)
        {
            try {
                target = readEntity(inputStream);
            } catch (InvalidPacketDataException e) {
                e.printStackTrace();
            }
        }

        float currentSpeed = dataInputStream.readFloat();
        int animalStateId = dataInputStream.readInt();
        if(animalStateId >= 0 && animalStateId < AnimalState.values().length)
        {
            animalState = AnimalState.values()[animalStateId];
        }else throw new InvalidPacketDataException();
        id = dataInputStream.readInt();
    }
}
