package com.cedricmartens.hectogon.server;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketInChat;
import com.cedricmartens.commons.networking.authentification.PacketInLogin;
import com.cedricmartens.hectogon.server.auth.AuthentificationService;
import com.cedricmartens.hectogon.server.auth.Authentificator;

import java.io.IOException;
import java.net.Socket;

public class SocketConnection implements SocketListener {

    private static final int ANONYMOUS_PLAYER = -1;

    private Socket socket;
    private int playerId = ANONYMOUS_PLAYER;
    private boolean listening;

    public SocketConnection(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void listen(Server server) {
        while (listening)
        {
            try {
                Packet packet = Packet.readHeader(socket.getInputStream());
                packet.readFrom(socket.getInputStream());

                if (packet instanceof PacketInLogin)
                {
                    PacketInLogin packetInLogin = (PacketInLogin) packet;
                    AuthentificationService authService = Authentificator.getAuthentificationService();
                    authService.login(packetInLogin.getUsername(), packetInLogin.getPassword());

                }else if(packet instanceof PacketInChat)
                {
                    PacketInChat packetInChat = (PacketInChat) packet;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvalidPacketDataException e) {
                e.printStackTrace();
            }
        }
    }
}
