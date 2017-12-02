package com.cedricmartens.hectogon.server.command;

import com.cedricmartens.commons.networking.competitor.DeathReason;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;

public class KillCommand extends Command {

    @Override
    void execute(String[] args) {
        int playerId = Integer.parseInt(args[0]);
        try {
            CommandCenter.server.getMatchById(0).removePlayer(playerId, DeathReason.KICKED);
        } catch (NoMatchFoundException e) {
            e.printStackTrace();
        }
    }
}
