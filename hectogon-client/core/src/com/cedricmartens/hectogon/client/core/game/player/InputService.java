package com.cedricmartens.hectogon.client.core.game.player;

public interface InputService
{
    default boolean left(int code) {
        return left(code, InputType.KEY);
    }
    boolean left(int code, InputType inputType);

    default boolean right(int code) {
        return right(code, InputType.KEY);
    }
    boolean right(int code, InputType inputType);

    default boolean up(int code) {
        return up(code, InputType.KEY);
    }
    boolean up(int code, InputType inputType);

    default boolean down(int code) {
        return down(code, InputType.KEY);
    }
    boolean down(int code, InputType inputType);

    default boolean toggleChatInput(int code) {
        return toggleChatInput(code, InputType.KEY);
    }
    boolean toggleChatInput(int code, InputType inputType);

    default boolean toggleChatBox(int code) {
        return toggleChatBox(code, InputType.KEY);
    }
    boolean toggleChatBox(int code, InputType inputType);

    default boolean openInventory(int code) { return openInventory(code, InputType.KEY); }
    boolean openInventory(int code, InputType inputType);
    //boolean openEquipment(int code);

    enum InputType{
        KEY,
        MOUSE
    }
}
