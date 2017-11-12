package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.hectogon.client.core.game.GameManager;
import com.cedricmartens.hectogon.client.core.game.Hectogon;
import com.cedricmartens.hectogon.client.core.ui.ChatInput;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;
import com.cedricmartens.hectogon.client.core.ui.OnSend;
import com.cedricmartens.hectogon.client.core.world.Map;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class WorldScreen extends StageScreen{

    private Map map;
    private SpriteBatch batch;
    private OrthographicCamera worldCamera;
    private boolean listening;
    public WorldScreen(GameManager gameManager)
    {
        super(gameManager);
        this.listening = true;
        this.map = new Map(gameManager.assetManager);
        this.batch = new SpriteBatch();
        this.worldCamera = new OrthographicCamera();
        this.worldCamera.setToOrtho(false);
        this.worldCamera.zoom = 0.25f;
        this.worldCamera.update();

        final Socket socket;
        try {
            socket = new Socket("127.0.0.1", 6666);
            final ChatInput chatInput = new ChatInput("", UiUtil.getChatSkin());
            chatInput.setWidth(Hectogon.WIDTH / 2.5f);
            chatInput.setX(15);
            chatInput.setY(15);
            chatInput.setOnSendAction(new OnSend() {
                @Override
                public void send() {
                    if (chatInput.getText().length() > 0) {
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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (listening) {
                        try {
                            Packet packet = Packet.readHeader(socket.getInputStream());
                            packet.readFrom(socket.getInputStream());

                            if (packet instanceof PacketChat) {
                                PacketChat packetChat = (PacketChat) packet;
                                System.out.println(packetChat.getMessage());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvalidPacketDataException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(this.worldCamera.combined);
        this.batch.begin();
        map.render(batch);
        this.batch.end();
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