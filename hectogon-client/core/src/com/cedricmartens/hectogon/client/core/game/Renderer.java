package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.cedricmartens.commons.entities.Rabbit;
import com.cedricmartens.commons.util.FuzzyDirection;
import com.cedricmartens.commons.util.MathUtil;

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
        FuzzyDirection fuzzyDirection = MathUtil.getFuzzyDirection(rabbit.getDirection().angleRad());
        switch (fuzzyDirection) {
            case RIGHT:
                break;
            case UP:
                break;
            case LEFT:
                break;
            case DOWN:
                break;
        }

    }
}
