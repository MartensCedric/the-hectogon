package com.cedricmartens.commons.networking.competitor;

import com.cedricmartens.commons.networking.InvalidPacketDataException;

import java.io.*;

public class PacketCompetitor extends PacketCompetitorJoin {

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        super.readFrom(inputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        competitor.setMovingLeft(dataInputStream.readBoolean());
        competitor.setMovingRight(dataInputStream.readBoolean());
        competitor.setMovingDown(dataInputStream.readBoolean());
        competitor.setMovingUp(dataInputStream.readBoolean());
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException
    {
        super.writeTo(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBoolean(competitor.isMovingLeft());
        dataOutputStream.writeBoolean(competitor.isMovingRight());
        dataOutputStream.writeBoolean(competitor.isMovingDown());
        dataOutputStream.writeBoolean(competitor.isMovingUp());
    }
}
