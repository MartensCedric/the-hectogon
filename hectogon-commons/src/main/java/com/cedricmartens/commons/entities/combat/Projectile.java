package com.cedricmartens.commons.entities.combat;

import com.cedricmartens.commons.entities.Entity;

public abstract class Projectile extends Entity{

    private int projectileId;
    protected float speed;
    protected float directionRad;


    @Override
    public int getId() {
        return projectileId;
    }

    @Override
    public void setId(int id) {
        this.projectileId = projectileId;
    }

    public void update(float deltaTime)
    {
        getPosition().x += Math.cos(directionRad) * speed * deltaTime;
        getPosition().y += Math.sin(directionRad) * speed * deltaTime;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getDirection() { return directionRad; }
}
