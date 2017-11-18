package com.cedricmartens.commons.networking.authentification;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

/**
 * Created by Cedric Martens on 2017-11-08.
 */
public class PacketOutLogin extends Packet {

    private LoginStatus loginStatus;
    private String token;
    private int userId;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int responseCode = dataInputStream.readInt();
        if(responseCode < 0 || responseCode >= LoginStatus.values().length)
            throw new InvalidPacketDataException(responseCode + " is not a valid value for LoginStatus");


        loginStatus = LoginStatus.values()[responseCode];
        token = dataInputStream.readUTF();
        userId = dataInputStream.readInt();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(loginStatus.ordinal());
        dataOutputStream.writeUTF(token);
        dataOutputStream.writeInt(userId);
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
