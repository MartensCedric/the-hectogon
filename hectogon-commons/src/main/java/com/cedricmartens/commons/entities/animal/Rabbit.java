package com.cedricmartens.commons.entities.animal;

import com.cedricmartens.commons.Health;
import com.cedricmartens.commons.util.Vector2;

public class Rabbit extends Animal {

    public Rabbit()
    {
        super(0, 0);
    }

    public Rabbit(float x, float y) {
        super(x, y);
        adrenalineSpeed = 80;
        wanderSpeed = 8;
        currentSpeed = wanderSpeed;
        animalState = AnimalState.WANDERING;
        direction = new Vector2().setToRandomDirection();
        health = new Health();
        health.setMaxHealth(5);
        health.setCurrentHealth(health.getMaxHealth());
        health.setRegenRate(0.02f);
    }
}
