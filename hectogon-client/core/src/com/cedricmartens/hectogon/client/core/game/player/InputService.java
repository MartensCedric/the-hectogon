package com.cedricmartens.hectogon.client.core.game.player;

public interface InputService
{
    boolean fire(int keyCode);
    boolean left(int keyCode);
    boolean right(int keyCode);
    boolean up(int keyCode);
    boolean down(int keyCode);
}
