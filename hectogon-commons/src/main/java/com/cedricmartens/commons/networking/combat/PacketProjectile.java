package com.cedricmartens.commons.networking.combat;

import com.cedricmartens.commons.entities.combat.Projectile;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PacketProjectile extends Packet
{
    private Projectile projectile;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String className = dataInputStream.readUTF();
        try {

            Class<? extends Projectile> projectileType = (Class<? extends Projectile>) Class.forName(className);
            Constructor<? extends Projectile> ctor = projectileType.getConstructor();
            this.projectile = ctor.newInstance();
            this.projectile.readFrom(inputStream);

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
        dataOutputStream.writeUTF(projectile.getClass().getName());
        projectile.writeTo(dataOutputStream);
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }
}
