package com.cedricmartens.commons.networking;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.entities.Entity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Serializer
{
    public static <T extends Entity> T readEntity(InputStream inputStream) throws InvalidPacketDataException, IOException {
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
}
