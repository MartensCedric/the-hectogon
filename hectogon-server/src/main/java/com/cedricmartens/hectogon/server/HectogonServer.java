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
        boolean listening = true;
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while(listening)
            {
                Socket socket = serverSocket.accept();
                Packet packet = Packet.readHeader(socket.getInputStream());
                packet.readFrom(socket.getInputStream());
                if(packet instanceof PacketInChat)
                {
                    System.out.println(((PacketInChat) packet).getMessage());
                }
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
        System.out.println("Hello World!");
    }
}
