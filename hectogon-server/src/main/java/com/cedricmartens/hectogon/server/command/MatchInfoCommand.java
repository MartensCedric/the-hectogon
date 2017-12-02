package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.hectogon.server.match.Match;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;

public class MatchInfoCommand extends Command {
    @Override
    void execute(String[] args) {
        int matchId = Integer.parseInt(args[0]);
        try {
            Match m = CommandCenter.server.getMatchById(matchId);

            //TODO match info
            /*
            This includes :
            Has the match started?
            If so, how much time?
            How much players at the start of the match?
            How much players are alive and dead?
            Who has the most kills?
             */

        } catch (NoMatchFoundException e) {
            e.printStackTrace();
        }
    }
}
