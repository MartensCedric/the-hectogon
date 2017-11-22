package com.cedricmartens.hectogon.client.core.game.player;

import com.cedricmartens.commons.networking.actions.MovementAction;

public interface MovementListener
{
    void move(MovementAction action);
}
