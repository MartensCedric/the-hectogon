package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Health;
import com.cedricmartens.commons.util.MathUtil;
import com.cedricmartens.commons.util.Vector2;

import java.util.List;

public abstract class Animal extends Entity
{
    protected int id;
    protected Health health;
    protected AnimalState animalState;
    protected Entity target;
    protected float wanderSpeed;
    protected float adrenalineSpeed;
    protected float currentSpeed;
    protected Vector2 direction;

    public Animal(float x, float y) {
        super(x, y);
    }

    public void update(float delta)
    {
        switch (animalState) {
            case IDLE:
                idle(delta);
                break;
            case WANDERING:
                wander(delta);
                break;
            case FIGHTING:
                break;
            case CHASING:
                break;
            case FLEEING:
                flee(delta);
                break;
            case LOOTING:
                break;
        }
    }

    protected void wander(float delta)
    {
        move(direction.angleRad(), currentSpeed, delta);
    }

    protected void idle(float delta)
    {

    }

    protected void flee(float delta)
    {
        move((float) (direction.angleRad() + 2 * Math.PI), currentSpeed, delta);
    }

    protected boolean checkForTarget(List<? extends Entity> entityList)
    {
        if(target == null)
        {
            for(Entity e : entityList)
            {
                if(!e.getClass().equals(getClass()))
                {
                    if(MathUtil.distanceToPoint(e.getPosition().x, e.getPosition().y,
                            getPosition().x, getPosition().y) < 50)
                    {
                        target = e;
                        getDirection().set(getPosition().x -  e.getPosition().x,
                                getPosition().y - e.getPosition().y);
                        getDirection().nor();
                        setCurrentSpeed(adrenalineSpeed);
                        animalState = AnimalState.FLEEING;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void updateState(List<? extends Entity> entityList)
    {
        if(checkForTarget(entityList))
            return;

        switch (animalState) {
            case IDLE:
                boolean changeState = MathUtil.randomBoolean(0.005f);
                if(changeState)
                {
                    animalState = AnimalState.WANDERING;
                    getDirection().setToRandomDirection();
                }
                break;
            case WANDERING:
                changeState = MathUtil.randomBoolean(0.0005f);
                if(changeState) animalState = AnimalState.IDLE;
                break;
            case FIGHTING:
                break;
            case CHASING:
                break;
            case FLEEING:
                if(MathUtil.distanceToPoint(target.getPosition().x, target.getPosition().y,
                        getPosition().x, getPosition().y) > 300)
                {
                    animalState = AnimalState.WANDERING;
                    target = null;
                    setCurrentSpeed(wanderSpeed);
                }else{
                    getDirection().set(getPosition().x -  target.getPosition().x,
                            getPosition().y - target.getPosition().y);
                    getDirection().nor();
                }

                break;
            case LOOTING:
                break;
        }
    }

    public void setAnimalState(AnimalState animalState) {
        this.animalState = animalState;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public AnimalState getAnimalState() {
        return animalState;
    }

    public float getWanderSpeed() {
        return wanderSpeed;
    }

    public float getAdrenalineSpeed() {
        return adrenalineSpeed;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }
}
