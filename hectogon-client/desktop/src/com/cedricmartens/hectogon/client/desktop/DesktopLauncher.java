package com.cedricmartens.hectogon.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cedricmartens.hectogon.client.core.game.Hectogon;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;

		config.foregroundFPS = 60;
		config.backgroundFPS = 60;

		config.width = 3840;
		config.height = 2160;

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

		while(display.getWidth() - 15 < config.width || display.getHeight() - 70 < config.height)
		{
			config.width -= 16;
			config.height -= 9;
		}

		if(config.width <= 0 || config.height <= 0)
			return;


		new LwjglApplication(new Hectogon(), config);
	}
}
