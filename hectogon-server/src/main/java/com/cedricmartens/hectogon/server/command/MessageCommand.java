package com.cedricmartens.hectogon.server.command;

/**
 * Created by 1544256 on 2017-11-09.
 */
public class MessageCommand extends Command{
    @Override
    void execute(String[] args) {

        if(args.length > 0)
        {
            for(int i = 0; i < args.length - 1; i++)
            {
                System.out.print(args[i] + " ");
            }

            System.out.print(args[args.length - 1]);

        }else{
            throw new RuntimeException();
        }
    }
}
