package com.cedricmartens.hectogon.client.core.game.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.actions.MovementAction;

public class Player extends Competitor implements InputProcessor {

    private MovementListener movementListener;

    public Player(User user, Point position, MovementListener movementListener) {
        super(user, position);
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        this.movementListener = movementListener;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(Input.Keys.LEFT == keycode)
        {
            movingLeft = true;
            movementListener.move(MovementAction.LEFT_KEY_PRESS);
        }

        if(Input.Keys.RIGHT == keycode)
        {
            movingRight = true;
            movementListener.move(MovementAction.RIGHT_KEY_PRESS);
        }

        if(Input.Keys.UP == keycode)
        {
            movingUp = true;
            movementListener.move(MovementAction.UP_KEY_PRESS);
        }

        if(Input.Keys.DOWN == keycode)
        {
            movingDown = true;
            movementListener.move(MovementAction.DOWN_KEY_PRESS);
        }

        return true;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(Input.Keys.LEFT == keycode)
        {
            movingLeft = false;
            movementListener.move(MovementAction.LEFT_KEY_RELEASED);
        }

        if(Input.Keys.RIGHT == keycode) {
            movingRight = false;
            movementListener.move(MovementAction.RIGHT_KEY_RELEASED);
        }

        if(Input.Keys.UP == keycode) {
            movingUp = false;
            movementListener.move(MovementAction.UP_KEY_RELEASED);
        }

        if(Input.Keys.DOWN == keycode) {
            movingDown = false;
            movementListener.move(MovementAction.DOWN_KEY_RELEASED);
        }

        return true;
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
