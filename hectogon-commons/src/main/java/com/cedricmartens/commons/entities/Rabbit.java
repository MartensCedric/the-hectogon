package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.util.Vector2;

public class Rabbit extends Animal {

    public Rabbit(float x, float y) {
        super(x, y);
        speed = 8;
        direction = new Vector2().setToRandomDirection();
        animalState = AnimalState.WANDERING;
    }
}
