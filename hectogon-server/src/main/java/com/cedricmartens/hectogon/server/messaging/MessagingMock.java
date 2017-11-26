package com.cedricmartens.hectogon.server.messaging;

import com.cedricmartens.commons.User;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.hectogon.server.match.Match;

public class MessagingMock implements MessagingService {

    public static final int MAX_CHARARACTERS = 280;

    @Override
    public void sendPM(User sender, User receiver, Match match, String contents) {

    }

    @Override
    public void sendGlobal(User sender, Match match, String contents) {
        PacketChat packetInChat = new PacketChat(verify(contents), sender.getUserId(), ChatType.GLOBAL);
        match.send(p -> !p.getUser().equals(sender), packetInChat);
    }

    @Override
    public void sendLocal(User sender, Match match, String contents) {
        PacketChat packetInChat = new PacketChat(verify(contents), sender.getUserId(), ChatType.LOCAL);
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
