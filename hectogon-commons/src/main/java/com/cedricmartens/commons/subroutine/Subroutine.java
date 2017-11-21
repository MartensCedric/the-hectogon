package com.cedricmartens.commons.subroutine;

/**
 * Created by Cedric Martens on 2017-11-21.
 */
public abstract class Subroutine
{
    private float timeLeft;

    public Subroutine(float duration)
    {
        timeLeft = duration;
        start();
    }

    public abstract void start();
    public abstract void next();
    public abstract void end();
}
