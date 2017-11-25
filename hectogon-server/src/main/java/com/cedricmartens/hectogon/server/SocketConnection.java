package com.cedricmartens.hectogon.server;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.networking.actions.MovementAction;
import com.cedricmartens.commons.networking.actions.PacketMovement;
import com.cedricmartens.commons.networking.authentification.*;
import com.cedricmartens.hectogon.server.auth.AuthentificationMock;
import com.cedricmartens.hectogon.server.auth.AuthentificationService;
import com.cedricmartens.hectogon.server.auth.DatabaseAuthentification;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;
import com.cedricmartens.hectogon.server.match.Player;
import com.cedricmartens.hectogon.server.messaging.MessagingMock;
import com.cedricmartens.hectogon.server.user.DatabaseUser;
import com.cedricmartens.hectogon.server.user.UserMock;
import com.cedricmartens.hectogon.server.user.UserNotFoundException;
import com.cedricmartens.hectogon.server.user.UserService;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.Socket;

public class SocketConnection implements SocketListener {

    private static final int ANONYMOUS_PLAYER = -1;

    private Socket socket;
    private int playerId = ANONYMOUS_PLAYER;
    private boolean listening = true;
    private Server server;
    private Player player;

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

        Log.info("Listening to socket : " + socket.getInetAddress());
        while (listening)
        {
            try {
                Packet packet = Packet.readHeader(socket.getInputStream());
                packet.readFrom(socket.getInputStream());

                if (packet instanceof PacketInLogin)
                {
                    PacketInLogin packetInLogin = (PacketInLogin) packet;
                    AuthentificationService authService = new DatabaseAuthentification();
                    LoginStatus loginStatus = authService.login(packetInLogin.getUsername(), packetInLogin.getPassword());

                    PacketOutLogin packetOutLogin = new PacketOutLogin();
                    packetOutLogin.setLoginStatus(loginStatus);
                    packetOutLogin.setToken("token");

                    Packet.writeHeader(PacketOutLogin.class, socket.getOutputStream());
                    packetOutLogin.writeTo(socket.getOutputStream());

                    if(loginStatus == LoginStatus.OK)
                    {
                        UserService userService = new DatabaseUser();
                        User user = userService.getUserByUsername(packetInLogin.getUsername());
                        Player player = new Player(this, user, new Point(0, 0));
                        this.player = player;
                        server.getNextAvailableMatch().addPlayer(player);
                    }

                }else if(packet instanceof PacketInRegister)
                {
                    PacketInRegister packetInRegister = (PacketInRegister) packet;
                    AuthentificationService authService = new DatabaseAuthentification();
                    RegisterStatus registerStatus = authService.register(packetInRegister.getUsername(),
                            packetInRegister.getEmail(), packetInRegister.getPassword());

                    PacketOutRegister packetOutRegister = new PacketOutRegister();
                    packetOutRegister.setRegisterStatus(registerStatus);
                    packetOutRegister.setToken("");
                    Packet.writeHeader(PacketOutRegister.class, socket.getOutputStream());
                    packetOutRegister.writeTo(socket.getOutputStream());

                    if(registerStatus == RegisterStatus.OK)
                    {
                        UserService userService = new DatabaseUser();
                        User user = userService.getUserByUsername(packetInRegister.getUsername());

                        Player player = new Player(this, user, new Point(0, 0));
                        this.player = player;
                        server.getNextAvailableMatch().addPlayer(player);
                    }
                }
                else if(packet instanceof PacketChat)
                {
                    PacketChat packetInChat = (PacketChat) packet;
                    Log.debug(socket.getInetAddress() + " -> " + packetInChat.getMessage());
                    UserService userService = new DatabaseUser();
                    User user = userService.getUserById(packetInChat.getSenderId());

                    try {

                        //TODO set matchId
                        new MessagingMock().sendLocal(
                                user, server.getMatchById(0),
                                packetInChat.getMessage());
                    } catch (NoMatchFoundException e) {
                        e.printStackTrace();
                    }
                }else if(packet instanceof PacketMovement) {
                    PacketMovement packetMovement = (PacketMovement) packet;
                    if (player != null) {
                        Log.trace("Someone did " + packetMovement.getMovementAction().name());
                        player.processMovement(packetMovement.getMovementAction());
                        server.getMatchById(0).send(p-> p != player, packetMovement);
                    } else {
                        throw new IllegalStateException();
                    }
                }

            }catch (IOException e) {
                listening = false;
                Log.info(this.playerId + " at " + this.getSocket().getInetAddress().getHostAddress() +" has disconnected");
                server.removeSocketConnection(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvalidPacketDataException e) {
                e.printStackTrace();
            } catch (NoMatchFoundException e) {
                e.printStackTrace();
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocketConnection that = (SocketConnection) o;

        return playerId == that.playerId;
    }

    @Override
    public int hashCode() {
        return playerId;
    }
}
