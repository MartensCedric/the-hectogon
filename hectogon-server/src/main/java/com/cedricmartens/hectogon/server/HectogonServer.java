package com.cedricmartens.hectogon.server;

import com.cedricmartens.hectogon.server.command.CommandCenter;
import com.esotericsoftware.minlog.Log;

public class HectogonServer
{
    public static void main(String[] args)
    {
        Log.TRACE();
        Log.filename = "server.log";
        CommandCenter commandCenter = CommandCenter.getCommandCenter();
        commandCenter.run();
    }
}
