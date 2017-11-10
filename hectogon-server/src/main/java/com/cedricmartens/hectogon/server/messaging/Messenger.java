package com.cedricmartens.hectogon.server.messaging;


/**
 * Created by Cedric Martens on 2017-11-10.
 */
public class Messenger
{
    private static MessagingService messagingService;
    public static MessagingService getMessagingService()
    {
        if(messagingService == null) {
            Messenger.messagingService = new MessagingMatch();
        }

        return messagingService;
    }


}
