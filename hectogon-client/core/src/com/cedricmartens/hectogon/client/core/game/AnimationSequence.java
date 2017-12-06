package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationSequence<T>
{
    private boolean isFinished;
    private float time;
    private Animation<T> animation;
    private float speed;

    public AnimationSequence(Animation<T> animation)
    {
        this.animation = animation;
        this.time = 0;
        this.setSpeed(1);
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
