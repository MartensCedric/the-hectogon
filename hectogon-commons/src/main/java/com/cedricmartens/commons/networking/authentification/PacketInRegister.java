package com.cedricmartens.commons.networking.authentification;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

public class PacketInRegister extends Packet {

    private String username;
    private String email;
    private String password;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        username = dataInputStream.readUTF();
        email = dataInputStream.readUTF();
        password = dataInputStream.readUTF();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(username);
        dataOutputStream.writeUTF(email);
        dataOutputStream.writeUTF(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
