package com.cedricmartens.hectogon.client.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketInChat;

import java.io.IOException;
import java.net.Socket;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			Packet packetChat = new PacketInChat("This is a test", 0, ChatType.GLOBAL);
			Packet.writeHeader(PacketInChat.class, socket.getOutputStream());
			packetChat.writeTo(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
