package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;

public abstract class Entity
{
    public Entity(float x, float y)
    {
        setPosition(new Point(x, y));
    }
    private Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
