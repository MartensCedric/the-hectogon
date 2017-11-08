package com.cedricmartens.hectogon.server.messaging;

import com.cedricmartens.commons.User;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketInChat;
import com.cedricmartens.hectogon.server.Match;
import com.cedricmartens.hectogon.server.Server;

public class MessagingImpl implements MessagingService {

    public static final int MAX_CHARARACTERS = 280;


    @Override
    public void sendPM(User sender, User receiver, Match match, String contents) {

    }

    @Override
    public void sendGlobal(User sender, Match match, String contents) {
        PacketInChat packetInChat = new PacketInChat(verify(contents), sender.getUserId(), ChatType.GLOBAL);
        match.sendToEveryone(packetInChat);
    }

    @Override
    public void sendLocal(User sender, Match match, String contents) {
        PacketInChat packetInChat = new PacketInChat(verify(contents), sender.getUserId(), ChatType.LOCAL);
        match.sendToEveryone(packetInChat);
    }

    public String verify(String content)
    {
        if(content.length() > MAX_CHARARACTERS)
        {
            return content.substring(0, MAX_CHARARACTERS - 1);
        }

        return content;
    }
}
