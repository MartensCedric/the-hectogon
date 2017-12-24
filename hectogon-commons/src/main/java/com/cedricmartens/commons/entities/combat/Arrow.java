package com.cedricmartens.commons.entities.combat;

public class Arrow extends Projectile
{
    /**
     * @param strength 0.0 to 1.0
     */
    public Arrow(float strength, float direction)
    {
        this.speed = 450 * strength; //Should be redone so strength has a minimum value
        this.directionRad = direction;
    }
}
