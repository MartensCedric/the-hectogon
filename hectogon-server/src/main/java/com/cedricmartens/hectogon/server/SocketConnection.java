package com.cedricmartens.hectogon.server;

import com.cedricmartens.commons.User;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.networking.authentification.LoginStatus;
import com.cedricmartens.commons.networking.authentification.PacketInLogin;
import com.cedricmartens.commons.networking.authentification.PacketInRegister;
import com.cedricmartens.commons.networking.authentification.RegisterStatus;
import com.cedricmartens.hectogon.server.auth.AuthentificationService;
import com.cedricmartens.hectogon.server.auth.Authentificator;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;
import com.cedricmartens.hectogon.server.messaging.Messenger;

import java.io.IOException;
import java.net.Socket;

public class SocketConnection implements SocketListener {

    private static final int ANONYMOUS_PLAYER = -1;

    private Socket socket;
    private int playerId = ANONYMOUS_PLAYER;
    private boolean listening = true;
    private Server server;

    public SocketConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
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

    public void sendPacket(Packet packet) throws IOException {
        Packet.writeHeader(packet.getClass(), socket.getOutputStream());
        packet.writeTo(socket.getOutputStream());
    }

    @Override
    public void listen(Server server) {

        System.out.println("Listening to socket : " + socket.getInetAddress());
        while (listening)
        {
            try {
                Packet packet = Packet.readHeader(socket.getInputStream());
                packet.readFrom(socket.getInputStream());

                if (packet instanceof PacketInLogin)
                {
                    PacketInLogin packetInLogin = (PacketInLogin) packet;
                    AuthentificationService authService = Authentificator.getAuthentificationService();
                    LoginStatus loginStatus = authService.login(packetInLogin.getUsername(), packetInLogin.getPassword());

                    if(loginStatus == LoginStatus.OK)
                    {
                        System.out.println("Logged in!");
                    }

                }else if(packet instanceof PacketInRegister)
                {
                    PacketInRegister packetInRegister = (PacketInRegister) packet;
                    AuthentificationService authService = Authentificator.getAuthentificationService();
                    RegisterStatus registerStatus = authService.register(packetInRegister.getUsername(),
                            packetInRegister.getEmail(), packetInRegister.getPassword());

                    if(registerStatus == RegisterStatus.OK)
                    {
                        System.out.println("Registered!");
                    }
                }
                else if(packet instanceof PacketChat)
                {
                    PacketChat packetInChat = (PacketChat) packet;
                    System.out.println(socket.getInetAddress() + " -> " + packetInChat.getMessage());
                    User user = new User();
                    user.setUserId(packetInChat.getSenderId());
                    user.setUsername("Someone");
                    try {
                        Messenger.getMessagingService().sendLocal(
                                user, server.getMatchById(0),
                                packetInChat.getMessage());
                    } catch (NoMatchFoundException e) {
                        e.printStackTrace();
                    }
                }

            }catch (IOException e) {
                e.printStackTrace();
                listening = false;
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
