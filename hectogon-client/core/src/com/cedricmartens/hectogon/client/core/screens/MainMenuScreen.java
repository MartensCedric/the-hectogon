package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.authentification.PacketInLogin;
import com.cedricmartens.commons.networking.authentification.PacketOutLogin;
import com.cedricmartens.hectogon.client.core.game.GameManager;
import com.cedricmartens.hectogon.client.core.game.Hectogon;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;

import java.io.IOException;
import java.net.Socket;

public class MainMenuScreen extends StageScreen
{
    private SpriteBatch batch;

    public MainMenuScreen(final GameManager gameManager) {
        super(gameManager);
        batch = new SpriteBatch();
        Skin skin = UiUtil.getDefaultSkin();

        Table tableAuth = new Table();
        Label lblUsername = new Label("Username", skin);
        Label lblPassword = new Label("Password ", skin);
        final TextField tfUsername = new TextField("", skin);
        final TextField tfPassword = new TextField("", skin);
        tfPassword.setPasswordCharacter('*');
        tfPassword.setPasswordMode(true);
        TextButton txtButtonConnect = new TextButton("Connect", skin);
        txtButtonConnect.addListener(new ClickListener()
             {
                 @Override
                 public void clicked(InputEvent event, float x, float y)
                 {
                     try {
                         gameManager.socket = new Socket("127.0.0.1", 6666);
                         PacketInLogin packetInLogin = new PacketInLogin();
                         packetInLogin.setUsername(tfUsername.getText());
                         packetInLogin.setPassword(tfPassword.getText());
                         Packet.writeHeader(PacketInLogin.class, gameManager.socket.getOutputStream());
                         packetInLogin.writeTo(gameManager.socket.getOutputStream());

                         Packet packet = Packet.readHeader(gameManager.socket.getInputStream());
                         packet.readFrom(gameManager.socket.getInputStream());
                         if(packet instanceof PacketOutLogin)
                         {
                             MainMenuScreen.this.getSceneManager().pushScreen(new WorldScreen(gameManager));
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
        );

        tableAuth.add(lblUsername);
        tableAuth.add(tfUsername).width(600).height(50);
        tableAuth.row();
        tableAuth.add(lblPassword);
        tableAuth.add(tfPassword).width(600).height(50);
        tableAuth.row();
        tableAuth.add(txtButtonConnect).colspan(2);
        tableAuth.setX(Hectogon.WIDTH / 2);
        tableAuth.setY(Hectogon.HEIGHT / 2);
        getStage().addActor(tableAuth);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
