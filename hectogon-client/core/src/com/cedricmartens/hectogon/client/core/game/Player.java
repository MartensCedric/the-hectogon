package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;

public class Player extends Contestant implements InputProcessor {

    public Player(User user, Point position) {
        super(user, position);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(Input.Keys.LEFT == keycode)
            move(0.15f, (float) Math.PI);

        if(Input.Keys.RIGHT == keycode)
            move(0.15f, 0);

        if(Input.Keys.UP == keycode)
            move(0.15f, (float) (Math.PI + Math.PI/2));

        if(Input.Keys.DOWN == keycode)
            move(0.15f, (float) Math.PI/2);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
