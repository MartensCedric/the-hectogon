package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.hectogon.server.match.Match;
import com.esotericsoftware.minlog.Log;

public class MatchesCommand extends Command{
    @Override
    void execute(String[] args) {

        if(CommandCenter.server == null)
        {
            Log.warn("Server not started!");
        }else{
            Log.trace("Matches : ");
            for(Match m : CommandCenter.server.getMatches())
            {
                Log.trace(m.toString());
            }
        }
    }
}
