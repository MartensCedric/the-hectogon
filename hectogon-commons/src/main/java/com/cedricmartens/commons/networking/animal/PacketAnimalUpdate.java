package com.cedricmartens.commons.networking.animal;

import com.cedricmartens.commons.entities.animal.Animal;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PacketAnimalUpdate extends Packet
{
    private Animal animal;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String className = dataInputStream.readUTF();
        try {

            Class<? extends Animal> animalType = (Class<? extends Animal>) Class.forName(className);
            Constructor<? extends Animal> ctor = animalType.getConstructor();
            this.animal = ctor.newInstance();
            this.animal.readFrom(inputStream);

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
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        animal.writeTo(dataOutputStream);
    }

    public void setAnimal(Animal animal)
    {
        this.animal = animal;
    }

    public Animal getAnimal()
    {
        return this.animal;
    }
}
