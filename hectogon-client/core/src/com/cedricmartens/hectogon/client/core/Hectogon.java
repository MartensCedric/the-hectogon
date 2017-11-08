package com.cedricmartens.hectogon.client.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.cedricmartens.hectogon.client.core.screens.MainMenuScreen;
import java.util.Stack;

public class Hectogon extends Game
{
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	private AssetManager assetManager;
	private Stack<Screen> screens;
	
	@Override
	public void create () {

		assetManager = new AssetManager();
		assetManager.finishLoading();

		this.screens = new Stack<Screen>();
		this.pushScreen(new MainMenuScreen());
	}

	public void pushScreen(Screen screen)
	{
		this.screens.push(screen);
		super.setScreen(screen);
	}

	public void popScreen()
	{
		if(this.screens.size() <= 1)
		{
			throw new GdxRuntimeException("Cannot pop the last screen!");
		}

		this.screens.pop();
		this.setScreen(screens.peek());
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
