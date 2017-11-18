package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;
import com.cedricmartens.hectogon.client.core.screens.MainMenuScreen;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

public class Hectogon extends SceneManager
{
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	private AssetManager assetManager;
	
	@Override
	public void create ()
	{
		super.create();
		this.assetManager = new AssetManager();

		GameManager gameManager = new GameManager();
		gameManager.assetManager = this.assetManager;
		gameManager.sceneManager = this;

		assetManager.load("test.png", Texture.class);
		assetManager.load("ui/inventory.png", Texture.class);
		assetManager.load("icons/perks/bloodthirst_perk.png", Texture.class);
		assetManager.load("icons/perks/hunter_perk.png", Texture.class);
		assetManager.load("icons/perks/tank_perk.png", Texture.class);
		assetManager.load("items/bow_wood.png", Texture.class);
		assetManager.load("items/bomb.png", Texture.class);
		assetManager.load("tiles/grass_tile.png", Texture.class);
		assetManager.load("tiles/grassflowers_tile.png", Texture.class);
		assetManager.load("interactive/chest.png", Texture.class);
		assetManager.load("character/dummy.png", Texture.class);

		assetManager.load("i18n/language", I18NBundle.class);
		assetManager.finishLoading();

		TextureUtil textureUtil = TextureUtil.getTextureUtil();
		textureUtil.setAssetManager(assetManager);

		gameManager.i18NBundle = assetManager.get("i18n/language", I18NBundle.class);
		this.pushScreen(new MainMenuScreen(gameManager));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
