package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Renderer
{
    private Batch batch;

    public Renderer()
    {

    }

    public void setBatch(Batch activeBatch) {
        this.batch = activeBatch;
    }
}
