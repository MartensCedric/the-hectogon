package com.cedricmartens.commons.networking.competitor;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.authentication.RegisterStatus;

import java.io.*;

public class PacketDeath extends Packet
{
    private int userId;
    private DeathReason deathReason;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        userId = dataInputStream.readInt();
        int deathReasonCode = dataInputStream.readInt();

        if(deathReasonCode < 0 || deathReasonCode >= RegisterStatus.values().length)
            throw new InvalidPacketDataException(deathReasonCode + " is not a valid value for DeathReason");

        deathReason = DeathReason.values()[deathReasonCode];
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(userId);
        dataOutputStream.writeInt(deathReason.ordinal());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public DeathReason getDeathReason() {
        return deathReason;
    }

    public void setDeathReason(DeathReason deathReason) {
        this.deathReason = deathReason;
    }
}
