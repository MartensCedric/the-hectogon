package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;

public abstract class Entity
{
    public Entity(float x, float y)
    {
        setPosition(new Point(x, y));
    }
    public Entity(Point position) { setPosition(position); }
    private Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void move(float direction, float speed,  float deltaTime)
    {
        position.x += Math.cos(direction) * speed * deltaTime;
        position.y += Math.sin(direction) * speed * deltaTime;
    }
}
