package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.util.Vector2;

public class Rabbit extends Animal {

    public Rabbit(float x, float y) {
        super(x, y);
        adrenalineSpeed = 80;
        wanderSpeed = 8;
        currentSpeed = wanderSpeed;
        animalState = AnimalState.WANDERING;
        direction = new Vector2().setToRandomDirection();

    }
}
