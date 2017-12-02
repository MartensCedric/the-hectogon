package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.hectogon.server.match.Match;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;

public class AlivePlayersCommand extends Command
{
    @Override
    void execute(String[] args) {

        int matchId = Integer.parseInt(args[0]);
        try {
            Match m  = CommandCenter.server.getMatchById(matchId);
            String[] alivePlayers = m.getAlivePlayerUsernames();

            System.out.println("The usernames of the currently " + alivePlayers.length + " alive players are :");
            for(String s : alivePlayers)
            {
                System.out.println(s);
            }

        } catch (NoMatchFoundException e) {
            e.printStackTrace();
        }
    }
}
