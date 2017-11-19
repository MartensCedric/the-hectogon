package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventorySlot;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

import static com.cedricmartens.hectogon.client.core.game.Hectogon.HEIGHT;

public class InventoryUI extends Table
{
    private InventorySlotImage[] inventorySlotImages;
    private Inventory inventory;
    private Item selectedItem;
    private final int ROWS = 3;
    private final int COLUMNS = 4;

    public InventoryUI(Inventory inventory)
    {
        this.inventory = inventory;
        inventorySlotImages = new InventorySlotImage[inventory.getSlotCount()];

        if(inventory.getSlotCount() != inventorySlotImages.length)
            throw new IllegalStateException();

        refresh();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureUtil textureUtil = TextureUtil.getTextureUtil();

        //TODO fix this bug, needs projection matrix
        if(selectedItem != null)
        {
            batch.draw(textureUtil.getItemTexture(selectedItem),
                    Gdx.input.getX(), HEIGHT - Gdx.input.getY());
        }
        super.draw(batch, parentAlpha);
    }

    public void refresh()
    {
        clearChildren();
        TextureUtil textureUtil = TextureUtil.getTextureUtil();
        int n = 0;
        for(final InventorySlot is : inventory)
        {
            inventorySlotImages[n] = new InventorySlotImage(is);
            Texture texture = textureUtil.getItemTexture(is.getItem());
            TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(texture));
            inventorySlotImages[n].setDrawable(trd);
            inventorySlotImages[n].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedItem = is.getItem();
                    is.clear();
                    refresh();
                }
            });

            n++;
        }

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLUMNS; j++)
            {
                add(inventorySlotImages[i * COLUMNS + j]).width(64).height(64).expand().fill();
            }
            row();
        }


    }
}
