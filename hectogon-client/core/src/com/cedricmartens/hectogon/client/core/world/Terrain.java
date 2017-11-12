package com.cedricmartens.hectogon.client.core.world;

import com.badlogic.gdx.graphics.Texture;

public class Terrain
{
    private float speed;
    private Texture texture;

    public Terrain() {
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
