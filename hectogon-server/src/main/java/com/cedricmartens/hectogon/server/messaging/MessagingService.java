package com.cedricmartens.hectogon.server.messaging;

import com.cedricmartens.commons.User;
import com.cedricmartens.hectogon.server.match.Match;

public interface MessagingService
{
    void sendPM(User sender, User receiver, Match match, String contents);
    void sendGlobal(User sender, Match match, String contents);
    void sendLocal(User sender, Match match, String contents);
}
