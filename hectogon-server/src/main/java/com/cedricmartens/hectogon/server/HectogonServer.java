package com.cedricmartens.hectogon.server;


public class HectogonServer
{
    public static void main(String[] args)
    {
        Server server = new Server(6666);
        new Thread(server).run();
    }
}
