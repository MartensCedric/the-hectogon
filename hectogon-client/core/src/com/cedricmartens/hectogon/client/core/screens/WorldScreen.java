package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.hectogon.client.core.game.SceneManager;
import com.cedricmartens.hectogon.client.core.ui.ChatInput;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;
import com.cedricmartens.hectogon.client.core.ui.OnSend;

import java.io.IOException;
import java.net.Socket;


public class WorldScreen extends StageScreen{

    public WorldScreen(SceneManager sceneManager)
    {
        super(sceneManager);
        final Socket socket;
        try {
            socket = new Socket("127.0.0.1", 6666);
            final ChatInput chatInput = new ChatInput("", UiUtil.getChatSkin());
            chatInput.setWidth(400);
            chatInput.setOnSendAction(new OnSend() {
                @Override
                public void send() {
                    if(chatInput.getText().length() > 0)
                    {
                        PacketChat packetChat = new PacketChat(chatInput.getText(), 0, ChatType.LOCAL);
                        try {
                            Packet.writeHeader(PacketChat.class, socket.getOutputStream());
                            packetChat.writeTo(socket.getOutputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            getStage().addActor(chatInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}