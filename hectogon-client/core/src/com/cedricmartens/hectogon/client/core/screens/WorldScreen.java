package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.storage.Chest;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.hectogon.client.core.game.GameManager;
import com.cedricmartens.hectogon.client.core.game.Hectogon;
import com.cedricmartens.hectogon.client.core.ui.ChatInput;
import com.cedricmartens.hectogon.client.core.ui.InventoryUI;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;
import com.cedricmartens.hectogon.client.core.ui.OnSend;
import com.cedricmartens.hectogon.client.core.world.Map;

import java.io.IOException;
import java.net.Socket;

import static com.cedricmartens.hectogon.client.core.game.Hectogon.WIDTH;

public class WorldScreen extends StageScreen{

    private Map map;
    private SpriteBatch batch;
    private ShapeRenderer debugRenderer;
    private OrthographicCamera worldCamera;
    private boolean listening;
    private AssetManager assetManager;
    private Socket socket;
    private InventoryUI inventoryUI;
    private Inventory playerInv;
    private Chest chest;

    public WorldScreen(GameManager gameManager)
    {
        super(gameManager);
        this.debugRenderer = new ShapeRenderer();
        this.debugRenderer.setAutoShapeType(true);
        this.chest = new Chest(12);
        this.chest.setPosition(new Point(0, 0));
        this.socket = gameManager.socket;
        this.listening = true;
        this.assetManager = gameManager.assetManager;
        this.map = new Map(this.assetManager);
        this.batch = new SpriteBatch();
        this.worldCamera = new OrthographicCamera();
        this.worldCamera.setToOrtho(false);
        this.worldCamera.zoom = 0.5f;
        this.worldCamera.position.x = -25;
        this.worldCamera.position.y = 0;
        this.worldCamera.update();
        this.playerInv = new Inventory(12);
        inventoryUI = new InventoryUI(playerInv);
        Texture textureInventory = gameManager.assetManager.get("ui/inventory.png", Texture.class);
        Drawable drawableInventory = new TextureRegionDrawable(new TextureRegion(
                textureInventory));
        inventoryUI.setBackground(drawableInventory);
        inventoryUI.setWidth(textureInventory.getWidth());
        inventoryUI.setHeight(textureInventory.getHeight());
        inventoryUI.setX(WIDTH - textureInventory.getWidth());
        inventoryUI.setY(0);
        getStage().addActor(inventoryUI);

        final ChatInput chatInput = new ChatInput("", UiUtil.getChatSkin());
        chatInput.setWidth(WIDTH / 2.5f);
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(this.worldCamera.combined);

        worldCamera.update();

        this.batch.begin();
        map.render(batch);
        batch.draw(assetManager.get("interactive/chest.png", Texture.class), chest.getPosition().x,chest.getPosition().y);
        batch.draw(assetManager.get("character/dummy.png", Texture.class), worldCamera.position.x - 16, worldCamera.position.y - 24);
        this.batch.end();
        this.debugRenderer.setProjectionMatrix(worldCamera.combined);
        this.debugRenderer.begin();
        this.debugRenderer.rect(chest.getPosition().x, chest.getPosition().y, 64, 64);
        this.debugRenderer.end();
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