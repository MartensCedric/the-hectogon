package com.cedricmartens.hectogon.client.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketInChat;
import com.cedricmartens.hectogon.client.core.screens.MainMenuScreen;
import com.cedricmartens.hectogon.client.core.screens.StageScreen;

import java.io.IOException;
import java.net.Socket;
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

		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			Packet packetChat = new PacketInChat("This is a test", 0, 0, ChatType.GLOBAL);
			Packet.writeHeader(PacketInChat.class, socket.getOutputStream());
			packetChat.writeTo(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}


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
