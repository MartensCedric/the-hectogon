package com.cedricmartens.hectogon.client.core.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.cedricmartens.commons.storage.inventory.Item;

import java.util.HashMap;

/**
 * Created by Cedric Martens on 2017-11-14.
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

    public Texture getItemTexture(Item item)
    {
        if(itemMap.containsKey(item))
        {
            return itemMap.get(item);
        }

        return assetManager.get("test.png", Texture.class);
    }

    private void initItemMap()
    {
        itemMap.put(Item.bomb, assetManager.get("items/bomb.png", Texture.class));
        itemMap.put(Item.bow_wood, assetManager.get("items/bow_wood.png", Texture.class));
    }


    public static TextureUtil getTextureUtil()
    {
        if(textureUtil == null)
        {
            textureUtil = new TextureUtil();
        }

        return textureUtil;
    }

}
