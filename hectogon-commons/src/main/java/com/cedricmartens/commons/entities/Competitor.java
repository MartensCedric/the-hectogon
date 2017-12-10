package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.networking.actions.MovementAction;

public class Competitor extends Entity
{
    protected User user;
    protected float speed;

    protected boolean movingUp;
    protected boolean movingDown;
    protected boolean movingLeft;
    protected boolean movingRight;

    public Competitor()
    {
        super();
    }

    public Competitor(User user, Point position)
    {
        super(position);
        this.user = user;
        this.speed = 170;
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



    public void move(float deltaTime)
    {
        float dirX = 0;
        float dirY = 0;

        if(movingLeft)
            dirX += -1;

        if(movingRight)
            dirX += 1;

        if(movingUp)
            dirY += 1;

        if(movingDown)
            dirY += -1;

        if(dirX != 0 || dirY != 0)
            applyForce((float) Math.atan2(dirY, dirX), deltaTime);
    }

    public User getUser() {
        return user;
    }

    private void applyForce(float direction, float deltaTime)
    {
        getPosition().x += Math.cos(direction) * speed * deltaTime;
        getPosition().y += Math.sin(direction) * speed * deltaTime;
    }

    public void correctPosition(Point p, long timeAtCorrection)
    {
        setPosition(p);
        long diff = System.currentTimeMillis() - timeAtCorrection;
        System.out.println("Correction delta has " + diff + "ms");
        move(diff/1000.0f);
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

    @Override
    public int getId() {
        return user.getUserId();
    }

    @Override
    public void setId(int id) {
        user.setUserId(id);
    }
}
