package com.cedricmartens.commons.networking.authentification;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

/**
 * Created by Cedric Martens on 2017-11-08.
 */
public class PacketInLogin extends Packet
{
    String username;
    String password;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.setUsername(dataInputStream.readUTF());
        this.setPassword(dataInputStream.readUTF());
        dataInputStream.close();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(getUsername());
        dataOutputStream.writeUTF(getPassword());
        dataOutputStream.close();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
