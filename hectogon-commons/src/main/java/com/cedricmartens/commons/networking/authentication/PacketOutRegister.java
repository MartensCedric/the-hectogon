package com.cedricmartens.commons.networking.authentication;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

public class PacketOutRegister extends Packet
{

    private RegisterStatus registerStatus;
    private String token;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int registerCode = dataInputStream.readInt();

        if(registerCode < 0 || registerCode >= RegisterStatus.values().length)
            throw new InvalidPacketDataException(registerCode + " is not a valid value for RegisterStatus");


        registerStatus = RegisterStatus.values()[registerCode];
        token = dataInputStream.readUTF();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(registerStatus.ordinal());
        dataOutputStream.writeUTF(token);
    }

    public RegisterStatus getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(RegisterStatus registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
