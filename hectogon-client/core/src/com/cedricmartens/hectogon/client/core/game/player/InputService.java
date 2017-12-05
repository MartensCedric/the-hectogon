package com.cedricmartens.hectogon.client.core.game.player;

public interface InputService
{
    boolean left(int keyCode);
    boolean right(int keyCode);
    boolean up(int keyCode);
    boolean down(int keyCode);

    boolean fire(int keyCode);

    boolean toggleChatInput(int keyCode);
    boolean toggleChatBox(int keyCode);
    //boolean openInventory(int keyCode);
    //boolean openEquipment(int keyCode);
}
