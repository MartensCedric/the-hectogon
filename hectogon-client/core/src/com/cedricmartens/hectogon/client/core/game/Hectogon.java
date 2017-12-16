package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;
import com.cedricmartens.hectogon.client.core.game.manager.GameManager;
import com.cedricmartens.hectogon.client.core.game.manager.SceneManager;
import com.cedricmartens.hectogon.client.core.graphics.animation.Animator;
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
		assetManager.load("ui/frame.png", Texture.class);

		assetManager.load("icons/backpack.png", Texture.class);
		assetManager.load("icons/perks/bloodthirst_perk.png", Texture.class);
		assetManager.load("icons/perks/hunter_perk.png", Texture.class);
		assetManager.load("icons/perks/tank_perk.png", Texture.class);

		assetManager.load("items/arr_wood.png", Texture.class);
		assetManager.load("items/arrow_wood.png", Texture.class);
		assetManager.load("items/apple.png", Texture.class);
		assetManager.load("items/bomb.png", Texture.class);
		assetManager.load("items/bomb_fusing_animation.png", Texture.class);
		assetManager.load("items/bow_wood.png", Texture.class);
		assetManager.load("items/banana.png", Texture.class);
		assetManager.load("items/carrot.png", Texture.class);
		assetManager.load("items/meat.png", Texture.class);
		assetManager.load("items/steel_sword.png", Texture.class);
		assetManager.load("items/wooden_shield.png", Texture.class);

		assetManager.load("tiles/grass_tile.png", Texture.class);
		assetManager.load("tiles/grassflowers_tile.png", Texture.class);

		assetManager.load("interactive/chest.png", Texture.class);
		assetManager.load("interactive/lootbag.png", Texture.class);
		assetManager.load("misc/startstone.png", Texture.class);
		assetManager.load("character/dummy.png", Texture.class);

		assetManager.load("animals/bunny/bunny-left.png", Texture.class);
		assetManager.load("animals/bunny/bunny-left-animation.png", Texture.class);
		assetManager.load("animals/bunny/bunny-right.png", Texture.class);
		assetManager.load("animals/bunny/bunny-right-animation.png", Texture.class);
		assetManager.load("animals/bunny/bunny-down.png", Texture.class);
		assetManager.load("animals/bunny/bunny-down-animation.png", Texture.class);
		assetManager.load("animals/bunny/bunny-up.png", Texture.class);
		assetManager.load("animals/bunny/bunny-up-animation.png", Texture.class);

		assetManager.load("i18n/language", I18NBundle.class);
		assetManager.load("cursors/main_cursor.png", Texture.class);
		assetManager.finishLoading();

		Animator animator = Animator.getAnimator();
		animator.initializeAnimator(assetManager);
		Pixmap pm = new Pixmap(Gdx.files.internal("cursors/main_cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
		pm.dispose();
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