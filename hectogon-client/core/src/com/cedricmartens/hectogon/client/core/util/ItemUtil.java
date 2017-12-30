package com.cedricmartens.hectogon.client.core.util;

import com.badlogic.gdx.utils.I18NBundle;
import com.cedricmartens.commons.storage.inventory.Item;

public class ItemUtil
{
    private static ItemUtil itemUtil;
    private I18NBundle i18NBundle;

    private ItemUtil()
    {

    }

    public static void createItemUtil(I18NBundle i18NBundle)
    {
        itemUtil = new ItemUtil();
        itemUtil.i18NBundle = i18NBundle;
    }

    public static ItemUtil getItemUtil() {
        return itemUtil;
    }

    public String getName(Item item)
    {
        return i18NBundle.get("name_" + item.name());
    }

    public String getDescription(Item item)
    {
        return i18NBundle.get("desc_" + item.name());
    }
}
