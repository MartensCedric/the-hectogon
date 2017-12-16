package com.cedricmartens.hectogon.client.core.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.I18NBundle;
import com.cedricmartens.hectogon.client.core.game.player.Player;

import java.net.Socket;

public class GameManager
{
    public AssetManager assetManager;
    public SceneManager sceneManager;
    public Socket socket;
    public I18NBundle i18NBundle;
    public Player player;
}
