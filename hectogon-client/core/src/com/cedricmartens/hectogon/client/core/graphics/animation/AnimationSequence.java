package com.cedricmartens.hectogon.client.core.graphics.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cedricmartens.commons.util.FuzzyDirection;

public abstract class AnimationSequence<T extends TextureRegion>
{
    private boolean isFinished;
    private float time;
    private Animation<T> animation;
    private float speed;

    public AnimationSequence()
    {
        this.time = 0;
        this.setSpeed(1);
    }

    public void setAnimation(Animation<T> animation)
    {
        this.animation = animation;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public void update(float delta)
    {
        this.time += delta * speed;
        if(this.time > animation.getAnimationDuration())
            isFinished = true;
    }

    public void draw(Batch batch, float x, float y)
    {
        batch.draw(getCurrentFrame(), x, y);
    }
    public abstract void draw(Batch batch);

    protected abstract void setAnimationFromFuzzyDirection(FuzzyDirection fuzzyDirection);

    public T getCurrentFrame()
    {
        return this.animation.getKeyFrame(time);
    }


    public boolean isFinished()
    {
        if(animation.getPlayMode() == Animation.PlayMode.NORMAL
                || animation.getPlayMode() == Animation.PlayMode.REVERSED)
            return isFinished;
        else return false;
    }
}
