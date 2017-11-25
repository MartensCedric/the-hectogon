package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.authentification.*;
import com.cedricmartens.commons.util.AuthentificationUtil;
import com.cedricmartens.hectogon.client.core.game.manager.GameManager;
import com.cedricmartens.hectogon.client.core.game.Hectogon;
import com.cedricmartens.hectogon.client.core.ui.TabbedPanel;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class MainMenuScreen extends StageScreen
{
    private SpriteBatch batch;
    private final static String SERVER_IP = "127.0.0.1";
    private final static int SERVER_PORT = 6666;
    private GameManager gameManager;

    public MainMenuScreen(final GameManager gameManager) {
        super(gameManager);
        batch = new SpriteBatch();
        this.gameManager = gameManager;
        Skin skin = UiUtil.getDefaultSkin();

        WidgetGroup widgetLogin = createLoginTable();
        WidgetGroup widgetRegister = createRegisterTable();
        TabbedPanel tabbedPanel = new TabbedPanel(
                new String[]{gameManager.i18NBundle.get("login"), gameManager.i18NBundle.get("register")},
                new Actor[]{widgetLogin, widgetRegister},
                skin
        );
        tabbedPanel.setX(Hectogon.WIDTH / 2);
        tabbedPanel.setY(Hectogon.HEIGHT / 2);

        getStage().addActor(tabbedPanel);
    }

    private WidgetGroup createLoginTable()
    {
        WidgetGroup widgetGroup = new WidgetGroup();

        Table tableAuth = new Table();
        Skin skin = UiUtil.getDefaultSkin();
        Label lblUsername = new Label(gameManager.i18NBundle.get("username"), skin);
        Label lblPassword = new Label(gameManager.i18NBundle.get("password"), skin);
        final Label lblError = new Label("", skin);
        lblError.setColor(Color.RED);
        final TextField tfUsername = new TextField("", skin);
        final TextField tfPassword = new TextField("", skin);
        tfPassword.setPasswordCharacter('*');
        tfPassword.setPasswordMode(true);
        final TextButton txtButtonConnect = new TextButton(gameManager.i18NBundle.get("connect"), skin);

        tableAuth.add(lblError).colspan(2);
        tableAuth.row();
        tableAuth.add(lblUsername);
        tableAuth.add(tfUsername).width(600).height(50);
        tableAuth.row();
        tableAuth.add(lblPassword);
        tableAuth.add(tfPassword).width(600).height(50);
        tableAuth.row();
        tableAuth.add(txtButtonConnect).colspan(2);

        txtButtonConnect.addListener(new ClickListener()
             {
                 @Override
                 public void clicked(InputEvent event, float x, float y)
                 {
                     try {
                         txtButtonConnect.setDisabled(true);
                         gameManager.socket = new Socket(SERVER_IP, SERVER_PORT);
                         PacketInLogin packetInLogin = new PacketInLogin();
                         packetInLogin.setUsername(tfUsername.getText());
                         packetInLogin.setPassword(AuthentificationUtil.sha256(tfPassword.getText()));
                         Packet.writeHeader(PacketInLogin.class, gameManager.socket.getOutputStream());
                         packetInLogin.writeTo(gameManager.socket.getOutputStream());

                         Packet packet = Packet.readHeader(gameManager.socket.getInputStream());
                         packet.readFrom(gameManager.socket.getInputStream());
                         if(packet instanceof PacketOutLogin)
                         {
                             PacketOutLogin packetOutLogin = (PacketOutLogin) packet;
                             LoginStatus ls = packetOutLogin.getLoginStatus();
                             switch (ls) {
                                 case OK:
                                     MainMenuScreen.this.getSceneManager().pushScreen(new WorldScreen(gameManager));
                                     break;
                                 case INCORRECT_INFO:
                                     lblError.setText(gameManager.i18NBundle.get("login_inc_info"));
                                     break;
                                 case BANNED:
                                     lblError.setText(gameManager.i18NBundle.get("login_banned"));
                                     break;
                                 case UNEXPECTED_ERROR:
                                     lblError.setText(gameManager.i18NBundle.get("unexpected_error"));
                                     break;
                             }


                             txtButtonConnect.setDisabled(false);
                         }

                     }catch (SocketException e)
                     {
                         lblError.setText(gameManager.i18NBundle.get("err_server"));
                     }
                     catch (IOException e) {
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

        widgetGroup.addActor(tableAuth);
        tableAuth.setY(-100);
        return widgetGroup;
    }

    private WidgetGroup createRegisterTable()
    {
        WidgetGroup widgetGroup = new WidgetGroup();
        Skin skin = UiUtil.getDefaultSkin();
        Table authTable = new Table();
        final TextButton txtButtonRegister = new TextButton(gameManager.i18NBundle.get("register"), skin);
        final Label lblError = new Label("", skin);
        lblError.setColor(Color.RED);
        Label lblUsername = new Label(gameManager.i18NBundle.get("username"), skin);
        Label lblPassword = new Label(gameManager.i18NBundle.get("password"), skin);
        Label lblPasswordConf = new Label(gameManager.i18NBundle.get("password_conf"), skin);
        Label lblEmail = new Label(gameManager.i18NBundle.get("email"), skin);

        final TextField tfUsername = new TextField("", skin);
        final TextField tfEmail = new TextField("", skin);
        final TextField tfPassword = new TextField("", skin);
        tfPassword.setPasswordCharacter('*');
        tfPassword.setPasswordMode(true);

        final TextField tfPasswordConf = new TextField("", skin);
        tfPasswordConf.setPasswordCharacter('*');
        tfPasswordConf.setPasswordMode(true);

        txtButtonRegister.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (tfPassword.getText().toString().equals(tfPasswordConf.getText().toString())) {

                    try {
                        gameManager.socket = new Socket(SERVER_IP, SERVER_PORT);

                    txtButtonRegister.setDisabled(true);

                    PacketInRegister packetInRegister = new PacketInRegister();
                    packetInRegister.setUsername(tfUsername.getText().toString());
                    packetInRegister.setEmail(tfEmail.getText().toString());
                    packetInRegister.setPassword(AuthentificationUtil.sha256(tfPassword.getText()));
                    Packet.writeHeader(PacketInRegister.class, gameManager.socket.getOutputStream());

                    packetInRegister.writeTo(gameManager.socket.getOutputStream());

                    Packet packet = Packet.readHeader(gameManager.socket.getInputStream());
                    packet.readFrom(gameManager.socket.getInputStream());

                    if(packet instanceof PacketOutRegister)
                    {
                        PacketOutRegister packetOutRegister = (PacketOutRegister) packet;
                        RegisterStatus registerStatus = packetOutRegister.getRegisterStatus();

                        switch (registerStatus) {
                            case OK:
                                MainMenuScreen.this.getSceneManager().pushScreen(new WorldScreen(gameManager));
                                break;
                            case USERNAME_TAKEN:
                                lblError.setText(gameManager.i18NBundle.get("username_taken"));
                                break;
                            case EMAIL_TAKEN:
                                lblError.setText(gameManager.i18NBundle.get("email_taken"));
                                break;
                            case BAD_USERNAME:
                                lblError.setText(gameManager.i18NBundle.get("bad_username"));
                                break;
                            case BAD_EMAIL:
                                lblError.setText(gameManager.i18NBundle.get("bad_email"));
                                break;
                            case BAD_PASSWORD:
                                lblError.setText(gameManager.i18NBundle.get("bad_password"));
                                break;
                            case UNEXPECTED_ERROR:
                                lblError.setText(gameManager.i18NBundle.get("unexpected_error"));
                                break;

                        }
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

                } else {
                    lblError.setText(gameManager.i18NBundle.get("pass_not_match"));
                }
            }
        });

        authTable.add(lblError).colspan(2);
        authTable.row();
        authTable.add(lblUsername);
        authTable.add(tfUsername).width(600).height(50);
        authTable.row();
        authTable.add(lblEmail);
        authTable.add(tfEmail).width(600).height(50);
        authTable.row();
        authTable.add(lblPassword);
        authTable.add(tfPassword).width(600).height(50);
        authTable.row();
        authTable.add(lblPasswordConf);
        authTable.add(tfPasswordConf).width(600).height(50);
        authTable.row();
        authTable.add(txtButtonRegister).colspan(2);

        widgetGroup.addActor(authTable);
        authTable.setY(-150);
        return widgetGroup;
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
