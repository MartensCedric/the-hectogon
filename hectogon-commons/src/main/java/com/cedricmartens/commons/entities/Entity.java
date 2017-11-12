package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;

public abstract class Entity
{
    private Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
