package com.cedricmartens.commons.networking.actions;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;

import java.io.*;

public class PacketMovement extends Packet
{
    private MovementAction movementAction;

    @Override
    public void readFrom(InputStream inputStream) throws IOException, InvalidPacketDataException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int movementValue = dataInputStream.readInt();

        if(movementValue < 0 || movementValue >= MovementAction.values().length)
        {
            throw new IllegalStateException();
        }

        movementAction = MovementAction.values()[movementValue];
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(movementAction.ordinal());
    }

    public MovementAction getMovementAction() {
        return movementAction;
    }

    public void setMovementAction(MovementAction movementAction) {
        this.movementAction = movementAction;
    }


}
