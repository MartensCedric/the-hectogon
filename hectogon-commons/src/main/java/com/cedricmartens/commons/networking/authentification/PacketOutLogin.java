package com.cedricmartens.commons.networking.authentification;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

/**
 * Created by 1544256 on 2017-11-08.
 */
public class PacketOutLogin extends Packet {

    private LoginStatus loginStatus;
    private String token;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int responseCode = dataInputStream.readInt();
        if(responseCode < 0 || responseCode >= LoginStatus.values().length)
            throw new InvalidPacketDataException(responseCode + " is not a valid value for LoginStatus");


        loginStatus = LoginStatus.values()[responseCode];
        token = dataInputStream.readUTF();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(loginStatus.ordinal());
        dataOutputStream.writeUTF(token);
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
