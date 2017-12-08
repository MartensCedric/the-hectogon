package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cedricmartens.commons.entities.Rabbit;
import com.cedricmartens.commons.util.FuzzyDirection;
import com.cedricmartens.commons.util.MathUtil;

import java.util.HashMap;

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
