package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UiUtil
{
    private static Skin defaultSkin;
    private static Skin chatSkin;

    public static Skin getDefaultSkin()
    {
        if(defaultSkin == null)
        {
            defaultSkin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        }
        return defaultSkin;
    }

    public static Skin getChatSkin()
    {
        if(chatSkin == null)
        {
            chatSkin = new Skin(Gdx.files.internal("skins/chat/message.json"));
        }

        return chatSkin;
    }
}
