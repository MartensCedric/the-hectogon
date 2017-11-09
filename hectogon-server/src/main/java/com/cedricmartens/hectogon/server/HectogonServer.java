package com.cedricmartens.hectogon.server;


import com.cedricmartens.hectogon.server.command.CommandCenter;

public class HectogonServer
{
    public static void main(String[] args)
    {
        CommandCenter commandCenter = new CommandCenter();
        commandCenter.run();
    }
}
