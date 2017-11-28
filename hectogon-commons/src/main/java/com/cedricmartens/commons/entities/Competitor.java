package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.networking.actions.MovementAction;

public class Competitor
{
    protected User user;
    protected Point position;
    protected float speed;

    protected boolean movingUp;
    protected boolean movingDown;
    protected boolean movingLeft;
    protected boolean movingRight;

    public Competitor(User user, Point position)
    {
        this.user = user;
        this.position = position;
        this.speed = 150;
    }

    public void processMovement(MovementAction movementAction)
    {
        switch (movementAction) {
            case UP_KEY_PRESS:
                movingUp = true;
                break;
            case RIGHT_KEY_PRESS:
                movingRight = true;
                break;
            case DOWN_KEY_PRESS:
                movingDown = true;
                break;
            case LEFT_KEY_PRESS:
                movingLeft = true;
                break;
            case UP_KEY_RELEASED:
                movingUp = false;
                break;
            case RIGHT_KEY_RELEASED:
                movingRight = false;
                break;
            case DOWN_KEY_RELEASED:
                movingDown = false;
                break;
            case LEFT_KEY_RELEASED:
                movingLeft = false;
                break;
            case ROLL:
                break;
        }
    }

    public void move(float direction, float deltaTime)
    {
        position.x += Math.cos(direction) * speed * deltaTime;
        position.y += Math.sin(direction) * speed * deltaTime;
    }

    public void move(float deltaTime)
    {
        if(movingLeft)
        {
            addForce(speed, (float) Math.PI, deltaTime);
        }

        if(movingRight)
        {
            addForce(speed, 0, deltaTime);
        }

        if(movingUp)
        {
            addForce(speed, (float) Math.PI / 2, deltaTime);
        }

        if(movingDown)
        {
            addForce(speed, (float) (Math.PI + Math.PI/2), deltaTime);
        }
    }

    public User getUser() {
        return user;
    }

    private void addForce(float speed, float direction, float deltaTime)
    {
        getPosition().x += Math.cos(direction) * speed * deltaTime;
        getPosition().y += Math.sin(direction) * speed * deltaTime;
    }

    public void correctPosition(Point p, long timeAtCorrection)
    {
        position.x = p.x;
        position.y = p.y;
        long diff = System.currentTimeMillis() - timeAtCorrection;
        System.out.println("Correction delta has " + diff + "ms");
        move(diff/60.0f);
    }


    public Point getPosition()
    {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competitor that = (Competitor) o;

        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
