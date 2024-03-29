package com.cedricmartens.hectogon.server;

import com.cedricmartens.commons.User;
import com.cedricmartens.commons.UserNotFoundException;
import com.cedricmartens.commons.entities.combat.Projectile;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.networking.actions.PacketCompetitorMovement;
import com.cedricmartens.commons.networking.actions.PacketMovement;
import com.cedricmartens.commons.networking.authentication.*;
import com.cedricmartens.commons.networking.combat.PacketProjectile;
import com.cedricmartens.commons.networking.inventory.PacketDropItem;
import com.cedricmartens.commons.networking.inventory.PacketLoot;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.server.auth.AuthenticationService;
import com.cedricmartens.hectogon.server.auth.DatabaseAuthentication;
import com.cedricmartens.hectogon.server.match.Match;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;
import com.cedricmartens.hectogon.server.match.Player;
import com.cedricmartens.hectogon.server.messaging.MessagingMock;
import com.cedricmartens.hectogon.server.user.DatabaseUser;
import com.cedricmartens.hectogon.server.user.UserService;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.Socket;

public class SocketConnection implements SocketListener {

    private static final int ANONYMOUS_PLAYER = -1;

    private Socket socket;
    private int playerId = ANONYMOUS_PLAYER;
    private boolean listening = true;
    private boolean updatingPlayer = true;
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
                Log.trace("Received packet of type : " + packet.getClass().getSimpleName());
                if (packet instanceof PacketInLogin)
                {
                    PacketInLogin packetInLogin = (PacketInLogin) packet;
                    AuthenticationService authService = new DatabaseAuthentication(server);
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

                        Log.info(user.getUsername() + " has logged in");
                        setPlayerId(user.getUserId());
                        Match match = server.getNextAvailableMatch();
                        Player player = new Player(this, user, match.getNextPlayerPosition());
                        this.player = player;
                        match.addPlayer(player);
                    }

                }else if(packet instanceof PacketInRegister)
                {
                    PacketInRegister packetInRegister = (PacketInRegister) packet;
                    AuthenticationService authService = new DatabaseAuthentication(server);
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

                        Log.info(user.getUsername() + " has registered");

                        setPlayerId(user.getUserId());

                        Match match = server.getNextAvailableMatch();
                        Player player = new Player(this, user, match.getNextPlayerPosition());
                        this.player = player;
                        match.addPlayer(player);
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
                        new MessagingMock().sendGlobal(
                                user, server.getMatchById(0),
                                packetInChat.getMessage());
                    } catch (NoMatchFoundException e) {
                        e.printStackTrace();
                    }
                }else if(packet instanceof PacketMovement) {
                    PacketMovement packetMovement = (PacketMovement) packet;
                    if (player != null) {

                        player.processMovement(packetMovement.getMovementAction());
                        PacketCompetitorMovement packetCompetitorMovement = new PacketCompetitorMovement();
                        packetCompetitorMovement.setMovementAction(packetMovement.getMovementAction());
                        packetCompetitorMovement.setUserId(playerId);
                        server.getMatchById(0).send(p-> !p.equals(player), packetCompetitorMovement);
                    } else {
                        throw new IllegalStateException();
                    }
                }else if(packet instanceof PacketDropItem)
                {
                    PacketDropItem packetDropItem = (PacketDropItem)packet;
                    server.getMatchById(0)
                            .dropItem(player, packetDropItem.getItem(), packetDropItem.getQty());
                }else if(packet instanceof PacketProjectile)
                {
                    PacketProjectile packetProjectile = (PacketProjectile)packet;
                    Projectile projectile = packetProjectile.getProjectile();

                    if(player.getId() != projectile.getId())
                        projectile.setSenderId(player.getId());

                    Match m = server.getMatchById(0);
                    m.addProjectile(projectile);
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
