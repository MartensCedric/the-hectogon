package com.cedricmartens.hectogon.client.core.game.player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultInput implements InputService {


    @Override
    public boolean toggleChatInput(int code, InputType inputType) {
        return inputType == InputType.KEY ? '\r' == code : false;
    }

    @Override
    public boolean toggleChatBox(int code, InputType inputType) {
        return inputType == InputType.KEY ? 99 == code : false;
    }

    @Override
    public boolean left(int code, InputType inputType) {
        return inputType == InputType.KEY ? LEFT == code || A == code : false;
    }

    @Override
    public boolean right(int code, InputType inputType) {
        return inputType == InputType.KEY ? RIGHT == code || D == code : false;
    }

    @Override
    public boolean up(int code, InputType inputType) {
        return inputType == InputType.KEY ? UP == code || W == code : false;
    }

    @Override
    public boolean down(int code, InputType inputType) {
        return inputType == InputType.KEY ? DOWN == code || S == code : false;
    }

    @Override
    public boolean openInventory(int code, InputType inputType) {
        return inputType == InputType.KEY ? 105 == code : false;
    }
}
