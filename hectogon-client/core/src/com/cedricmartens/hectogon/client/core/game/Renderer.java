package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.cedricmartens.commons.entities.Rabbit;

public class Renderer
{
    private Batch batch;

    public Renderer()
    {

    }

    public void setBatch(Batch activeBatch) {
        this.batch = activeBatch;
    }

    public void renderRabbit(Rabbit rabbit)
    {
        Animator animator = Animator.getAnimator();

    }
}
