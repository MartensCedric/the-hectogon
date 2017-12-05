package com.cedricmartens.hectogon.client.core.game.player;

import com.badlogic.gdx.Input;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultInput implements InputService {
    @Override
    public boolean fire(int keyCode)
    {
        return SPACE == keyCode || LEFT == keyCode;
    }

    @Override
    public boolean toggleChatInput(int keyCode) {
        return '\r' == keyCode;
    }

    @Override
    public boolean toggleChatBox(int keyCode)
    {
        return 99 == keyCode; // 99 is lower case 'C'
    }

    @Override
    public boolean left(int keyCode) {
        return LEFT == keyCode || A == keyCode;
    }

    @Override
    public boolean right(int keyCode) {
        return RIGHT == keyCode || D == keyCode;
    }

    @Override
    public boolean up(int keyCode) {
        return UP == keyCode || W == keyCode;
    }

    @Override
    public boolean down(int keyCode) {
        return DOWN == keyCode || S == keyCode;
    }
}
