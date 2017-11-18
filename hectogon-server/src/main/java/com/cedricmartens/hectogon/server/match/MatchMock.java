package com.cedricmartens.hectogon.server.match;

/**
 * Created by Cedric Martens on 2017-11-13.
 */
public class MatchMock implements MatchService {
    private static int matchId = 0;
    @Override
    public Match createMatch() {
        return new Match(matchId++);
    }

    @Override
    public void archiveMatch() {

    }

    @Override
    public void startMatch(Match match) {

    }
}
