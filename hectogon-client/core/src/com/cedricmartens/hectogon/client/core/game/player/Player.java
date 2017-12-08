package com.cedricmartens.hectogon.client.core.game.player;

import com.badlogic.gdx.InputProcessor;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.actions.MovementAction;
import com.cedricmartens.hectogon.client.core.util.ServiceUtil;

public class Player extends Competitor implements InputProcessor {

    private MovementListener movementListener;
    private boolean inputEnabled;

    public Player(User user, Point position, MovementListener movementListener) {
        super(user, position);
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        this.movementListener = movementListener;
        this.inputEnabled = true;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(inputEnabled)
        {
            InputService inputService = ServiceUtil.getServiceUtil().getInputService();
            if(inputService.left(keycode))
            {
                movingLeft = true;
                movementListener.move(MovementAction.LEFT_KEY_PRESS);
            }

            if(inputService.right(keycode))
            {
                movingRight = true;
                movementListener.move(MovementAction.RIGHT_KEY_PRESS);
            }

            if(inputService.up(keycode))
            {
                movingUp = true;
                movementListener.move(MovementAction.UP_KEY_PRESS);
            }

            if(inputService.down(keycode))
            {
                movingDown = true;
                movementListener.move(MovementAction.DOWN_KEY_PRESS);
            }

        }
        return true;
    }

    public void setUser(User user)
    {
        this.user = user;
    }


    @Override
    public boolean keyUp(int keycode)
    {
        if(inputEnabled)
        {
            InputService inputService = ServiceUtil.getServiceUtil().getInputService();
            if(inputService.left(keycode))
            {
                movingLeft = false;
                movementListener.move(MovementAction.LEFT_KEY_RELEASED);
            }

            if(inputService.right(keycode)) {
                movingRight = false;
                movementListener.move(MovementAction.RIGHT_KEY_RELEASED);
            }

            if(inputService.up(keycode)) {
                movingUp = false;
                movementListener.move(MovementAction.UP_KEY_RELEASED);
            }

            if(inputService.down(keycode)) {
                movingDown = false;
                movementListener.move(MovementAction.DOWN_KEY_RELEASED);
            }
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

    public void setInputEnabled(boolean inputEnabled)
    {
        this.inputEnabled = inputEnabled;
        if(!inputEnabled)
        {
            this.movingDown = false;
            this.movingUp = false;
            this.movingLeft = false;
            this.movingRight = false;

            movementListener.move(MovementAction.LEFT_KEY_RELEASED);
            movementListener.move(MovementAction.RIGHT_KEY_RELEASED);
            movementListener.move(MovementAction.UP_KEY_RELEASED);
            movementListener.move(MovementAction.DOWN_KEY_RELEASED);
        }
    }
}