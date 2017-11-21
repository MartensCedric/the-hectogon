package com.cedricmartens.hectogon.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cedricmartens.hectogon.client.core.game.Hectogon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		//config.width = 1920;
		//config.height = 1080;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Hectogon(), config);
	}
}
