package com.cedricmartens.hectogon.server;

import java.net.Socket;

public class SocketConnection {

    private static final int ANONYMOUS_PLAYER = -1;

    private Socket socket;
    private int playerId = ANONYMOUS_PLAYER;

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
}
