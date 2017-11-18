package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.hectogon.server.match.Match;

public class ListMatchesCommand extends Command{
    @Override
    void execute(String[] args) {

        if(CommandCenter.server == null)
        {
            System.out.println("Server not started!");
        }else{
            System.out.println("Matches : ");
            for(Match m : CommandCenter.server.getMatches())
            {
                System.out.println(m.toString());
            }
        }
    }
}
