package com.cedricmartens.hectogon.server;

import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketInChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HectogonServer
{
    public static void main(String[] args)
    {
        Server server = new Server(6666);
        new Thread(server).run();
    }
}
