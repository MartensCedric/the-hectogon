package com.cedricmartens.commons.storage.inventory;

/**
 * Created by Cedric Martens on 05/03/17.
 */
public enum  Item {

    empty_slot("Empty Slot", "An item could be placed here", 0),
    swd_wood("Wooden Sword", "A pointy stick", 1),
    swd_steel("Steel Sword", "A sharp sword made of steel", 1),
    swd_copper("Copper Sword", "A sword made of copper", 1),
    bow_wood("Wooden Bow", "A curved stick with a string, could be useful with some arrows", 1),
    arr_wood("Wooden Arrow", "Bow ammunition", 16),
    dagger_wood("Wooden Dagger", "A sharp small chunk of wood", 1),
    dagger_copper("Copper Dagger", "A dagger made out of copper", 1),
    land_mine("Land mine", "Very powerful explosive, needs to be stepped on to be triggered", 4),
    bomb("Bomb", "Produces a large blast.", 1),
    fish_rod("Fishing rod", "A tool to catch the fish", 1);

    private String name;
    private String desc;
    private int stackMax;

    Item(String name, String desc, int stackMax)
    {
        this.name = name;
        this.desc = desc;
        this.stackMax = stackMax;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return desc;
    }

    public int getStackMax()
    {
        return stackMax;
    }

    @Override
    public String toString() {
        return name + "\n" + desc + ".\nStack : " + stackMax;
    }
}