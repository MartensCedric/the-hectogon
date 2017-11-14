package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.commons.User;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;
import com.cedricmartens.hectogon.server.messaging.MessagingMock;
import com.cedricmartens.hectogon.server.messaging.MessagingService;

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
            MessagingService messagingService = new MessagingMock();
            User user = new User(0, "Loomy");

            //TODO not match 0
            try {
                messagingService.sendGlobal(user, CommandCenter.server.getMatchById(0), contents);
            } catch (NoMatchFoundException e) {
                e.printStackTrace();
            }

        }else{
            throw new RuntimeException();
        }
    }
}
