package com.cedricmartens.hectogon.client.core.game.player;

import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.actions.MovementAction;
import com.cedricmartens.commons.networking.actions.PacketMovement;

import java.io.IOException;
import java.net.Socket;

public class NetworkMovementListener implements MovementListener {

    private Socket socket;

    public NetworkMovementListener(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void move(MovementAction action) {
        PacketMovement packetMovement = new PacketMovement();
        packetMovement.setMovementAction(action);
        try {
            Packet.writeHeader(PacketMovement.class, socket.getOutputStream());
            packetMovement.writeTo(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
