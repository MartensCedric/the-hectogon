package com.cedricmartens.commons.entities;

public abstract class SolidEntity extends Entity implements Hitbox
{
    public SolidEntity(float x, float y) {
        super(x, y);
    }
}