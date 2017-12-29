package com.cedricmartens.commons.storage.inventory;

/**
 * Created by Cedric Martens on 05/03/17.
 */
public enum  Item {

    empty_slot(0),
    swd_steel(1),
    swd_gold(1),
    spear(1),
    shield_reinforced(1),
    meat(1),
    stick_wood(8),
    bow_wood(1),
    arr_wood(16),
    bomb(1),
    fish_rod(1),
    carrot(4),
    golden_carrot(1),
    banana(4),
    apple(1),
    ;

    private int stackMax;

    Item(int stackMax)
    {
        this.stackMax = stackMax;
    }

    public int getStackMax()
    {
        return stackMax;
    }
}