package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.commons.User;
import com.cedricmartens.hectogon.server.messaging.MessagingService;
import com.cedricmartens.hectogon.server.messaging.Messenger;

/**
 * Created by Cedric Martens on 2017-11-09.
 */
public class MessageCommand extends Command{

    @Override
    void execute(String[] args) {

        if(args.length > 0)
        {
            String contents = "";
            for(int i = 0; i < args.length - 1; i++)
            {
                contents += args[i] + " ";
            }

            contents += args[args.length - 1];
            MessagingService messagingService = Messenger.getMessagingService();
            User user = new User();
            user.setUserId(0);
            user.setUsername("Loomy");

            messagingService.sendGlobal(user, CommandCenter.server.getMatchById(0), contents);

        }else{
            throw new RuntimeException();
        }
    }
}
