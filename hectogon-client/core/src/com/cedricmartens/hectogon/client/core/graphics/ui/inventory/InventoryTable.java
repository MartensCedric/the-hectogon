package com.cedricmartens.hectogon.client.core.graphics.ui.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

public class InventoryTable extends Table
{
    protected InventorySlotImage[] inventorySlotImages;
    protected Inventory inventory;
    protected final int ROWS = 3;
    protected final int COLUMNS = 4;
    protected InventorySlotImage currentHover = null;
    protected Label lblHover = null;

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
        inventorySlotImages = new InventorySlotImage[inventory.getSlotCount()];
        init();
        redraw();
    }

    @Override
    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        super.drawBackground(batch, parentAlpha, x, y + 1);
    }

    protected void init()
    {
        int n = 0;
        for(final InventorySlot is : inventory)
        {
            inventorySlotImages[n] = new InventorySlotImage(is);
            n++;
        }

        clearChildren();

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLUMNS; j++)
            {
                InventorySlotImage inventorySlotImage = inventorySlotImages[i * COLUMNS + j];
                inventorySlotImage.addListener(new ClickListener(){
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        super.enter(event, x, y, pointer, fromActor);
                        System.out.println(inventorySlotImage.getInventorySlot().getItem().name());
                    }
                });
                add(inventorySlotImage).width(64).height(64).pad(5);
            }
            row();
        }
    }

    public void redraw()
    {
        TextureUtil textureUtil = TextureUtil.getTextureUtil();

        int n = 0;
        for(InventorySlot is : inventory)
        {
            inventorySlotImages[n].setInventorySlot(is);
            if(is.getItem() != Item.empty_slot)
            {
                Texture textureItem = textureUtil.getItemTexture(is.getItem());
                Drawable drawable = new TextureRegionDrawable(new TextureRegion(textureItem));
                inventorySlotImages[n].setDrawable(drawable);
            }else{
                inventorySlotImages[n].setDrawable(null);
            }
            n++;
        }
    }

}
