package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.cedricmartens.commons.storage.inventory.Item;

import java.util.HashMap;

/**
 * Created by 1544256 on 2017-11-14.
 */
public class TextureUtil
{
    private static TextureUtil textureUtil;
    private AssetManager assetManager;
    private HashMap<Item, Texture> itemMap;

    private TextureUtil() {
        itemMap = new HashMap<Item, Texture>();
    }

    public void setAssetManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
        initItemMap();
    }

    private void initItemMap()
    {
        itemMap.put(Item.bomb, assetManager.get("items/bomb.png", Texture.class));
        itemMap.put(Item.bow_wood, assetManager.get("items/bow_wood.png", Texture.class));
    }


    public static TextureUtil getTextureUtil()
    {
        if(textureUtil == null)
            throw new IllegalStateException();

        return textureUtil;
    }

}
