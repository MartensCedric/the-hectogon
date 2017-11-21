package com.cedricmartens.hectogon.client.core.game.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;

public class Player extends Contestant implements InputProcessor {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public Player(User user, Point position) {
        super(user, position);
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(Input.Keys.LEFT == keycode)
            leftPressed = true;

        if(Input.Keys.RIGHT == keycode)
            rightPressed = true;

        if(Input.Keys.UP == keycode)
            upPressed = true;

        if(Input.Keys.DOWN == keycode)
            downPressed = true;

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(Input.Keys.LEFT == keycode)
            leftPressed = false;

        if(Input.Keys.RIGHT == keycode)
            rightPressed = false;

        if(Input.Keys.UP == keycode)
            upPressed = false;

        if(Input.Keys.DOWN == keycode)
            downPressed = false;

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

    public void move(float speed, float deltaTime)
    {
        if(leftPressed)
        {
            addForce(speed, (float) Math.PI, deltaTime);
        }

        if(rightPressed)
        {
            addForce(speed, 0, deltaTime);
        }

        if(upPressed)
        {
            addForce(speed, (float) Math.PI / 2, deltaTime);
        }

        if(downPressed)
        {
            addForce(speed, (float) (Math.PI + Math.PI/2), deltaTime);
        }
    }

    private void addForce(float speed, float direction, float deltaTime)
    {
        getPosition().x += Math.cos(direction) * speed * deltaTime;
        getPosition().y += Math.sin(direction) * speed * deltaTime;
    }
}
