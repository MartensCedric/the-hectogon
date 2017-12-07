package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Health;
import com.cedricmartens.commons.util.Vector2;

public abstract class Animal extends Entity
{
    protected int id;
    protected Health health;
    protected AnimalState animalState;
    protected Entity target;
    protected float speed;
    protected Vector2 direction;

    public Animal(float x, float y) {
        super(x, y);
    }

    public void update(float delta) {
        switch (animalState) {
            case IDLE:
                break;
            case WANDERING:
                wander(delta);
                break;
            case FIGHTING:
                break;
            case CHASING:
                break;
            case FLEEING:
                break;
            case LOOTING:
                break;
        }

    }

    protected void wander(float delta)
    {
        move(direction.angleRad(), speed, delta);
    }

    public void setAnimalState(AnimalState animalState) {
        this.animalState = animalState;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }
}
