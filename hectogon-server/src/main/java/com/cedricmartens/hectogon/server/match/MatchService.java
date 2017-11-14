package com.cedricmartens.hectogon.server.match;

/**
 * Created by Cedric Martens on 2017-11-13.
 */
public interface MatchService {
    Match createMatch();
    void archiveMatch();
    void startMatch(Match match);
}
