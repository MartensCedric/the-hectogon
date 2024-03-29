package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.hectogon.server.Server;

/**
 * Created by Cedric Martens on 2017-11-10.
 */
public class LaunchCommand extends Command
{
    @Override
    void execute(String[] args) {

        if(CommandCenter.server != null)
        {
            System.out.println("Server already started!");
        }else if(args.length > 0)
        {
            int port = Integer.parseInt(args[0]);
            CommandCenter.server = new Server(port);
            new Thread(CommandCenter.server).start();
        }else{
        }
    }
}
