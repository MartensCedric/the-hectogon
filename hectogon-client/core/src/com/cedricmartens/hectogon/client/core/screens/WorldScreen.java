package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.entities.Entity;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.storage.Chest;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.client.core.game.player.Contestant;
import com.cedricmartens.hectogon.client.core.game.manager.GameManager;
import com.cedricmartens.hectogon.client.core.game.player.Player;
import com.cedricmartens.hectogon.client.core.ui.chat.ChatInput;
import com.cedricmartens.hectogon.client.core.ui.inventory.InventoryUI;
import com.cedricmartens.hectogon.client.core.ui.chat.OnSend;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;
import com.cedricmartens.hectogon.client.core.world.Map;
import com.cedricmartens.hectogon.client.core.world.StartStone;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.cedricmartens.hectogon.client.core.game.Hectogon.WIDTH;
import static java.lang.Math.PI;

public class WorldScreen extends StageScreen {

    private Map map;
    private SpriteBatch batch, uiBatch;
    private ShapeRenderer debugRenderer;
    private OrthographicCamera worldCamera;
    private boolean listening;
    private AssetManager assetManager;
    private Socket socket;
    private InventoryUI inventoryUI;
    private Inventory playerInv;
    private Chest chest;
    private List<Contestant> contestants;
    private List<Entity> decorations;
    private Player player;

    public WorldScreen(GameManager gameManager)
    {
        super(gameManager);
        this.contestants = new ArrayList<Contestant>();
        this.player = new Player(new User(0, "Loomy"), new Point(0, 0));
        this.decorations = new ArrayList<Entity>();
        for(int i = 0; i < 100; i++)
        {
            float x = Math.round(2500 * Math.cos((PI * i) / (100 / 2)));
            float y = Math.round(2500 * Math.sin((PI * i) / (100 / 2)));
            this.decorations.add(new StartStone(x, y));
        }
        this.contestants.add(player);

        this.debugRenderer = new ShapeRenderer();
        this.debugRenderer.setAutoShapeType(true);
        this.chest = new Chest(12);
        this.chest.setPosition(new Point(0, 0));
        this.socket = gameManager.socket;
        this.listening = true;
        this.assetManager = gameManager.assetManager;
        this.map = new Map(this.assetManager);
        this.batch = new SpriteBatch();
        this.uiBatch = new SpriteBatch();
        this.worldCamera = new OrthographicCamera();
        this.worldCamera.setToOrtho(false);
        this.worldCamera.zoom = .5f;
        this.worldCamera.position.x = -25;
        this.worldCamera.position.y = 0;
        this.worldCamera.update();
        this.playerInv = new Inventory(12);
        this.playerInv.addItem(Item.bow_wood);
        this.playerInv.addItem(Item.bomb);
        inventoryUI = new InventoryUI(playerInv);
        Texture textureInventory = gameManager.assetManager.get("ui/inventory.png", Texture.class);
        Drawable drawableInventory = new TextureRegionDrawable(new TextureRegion(
                textureInventory));
        inventoryUI.setBackground(drawableInventory);
        inventoryUI.setWidth(textureInventory.getWidth() * 2);
        inventoryUI.setHeight(textureInventory.getHeight() * 2);
        inventoryUI.setX(WIDTH - textureInventory.getWidth() * 2);
        inventoryUI.setY(0);
        inventoryUI.setDebug(true);
        inventoryUI.debugTable();

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

        Texture playerDummy = assetManager.get("character/dummy.png", Texture.class);
        player.move(150, delta);
        worldCamera.position.x = player.getPosition().x + playerDummy.getWidth() / 2;
        worldCamera.position.y = player.getPosition().y + playerDummy.getHeight() / 2;
        worldCamera.update();

        batch.setProjectionMatrix(this.worldCamera.combined);
        this.batch.begin();
        map.render(batch);
        for(Entity e : decorations)
        {
            if(e instanceof StartStone)
            {
                Texture texStartStone = assetManager.get("misc/startstone.png", Texture.class);
                batch.draw(texStartStone,
                        e.getPosition().x - texStartStone.getWidth()/2,
                        e.getPosition().y - texStartStone.getHeight()/2);
            }
        }
        batch.draw(assetManager.get("interactive/chest.png", Texture.class), chest.getPosition().x,chest.getPosition().y);
        for(Contestant c : contestants)
        {
            batch.draw(playerDummy, c.getPosition().x, c.getPosition().y);
        }
        this.batch.end();
        this.debugRenderer.setProjectionMatrix(worldCamera.combined);
        this.debugRenderer.begin();
        this.debugRenderer.rect(chest.getPosition().x, chest.getPosition().y, 64, 64);
        this.debugRenderer.rect(player.getPosition().x, player.getPosition().y, playerDummy.getWidth(), playerDummy.getHeight());
        this.debugRenderer.end();

        super.render(delta);

        if(this.inventoryUI.getSelectedItem() != null)
        {
            this.uiBatch.setProjectionMatrix(getStage().getCamera().combined);
            this.uiBatch.begin();
            TextureUtil textureUtil = TextureUtil.getTextureUtil();
            Texture textureItem = textureUtil.getItemTexture(this.inventoryUI.getSelectedItem());
            Viewport viewPort = getStage().getViewport();
            Vector3 pos = getStage().getCamera().unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0),
                    viewPort.getScreenX(), viewPort.getScreenY(),
                    viewPort.getScreenWidth(), viewPort.getScreenHeight());
            this.uiBatch.draw(textureItem, pos.x - textureItem.getWidth(), pos.y - textureItem.getHeight(),
                    textureItem.getWidth() * 2, textureItem.getHeight() * 2);
            this.uiBatch.end();
        }
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

    @Override
    public void show()
    {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(player);
        multiplexer.addProcessor(this.getStage());
        Gdx.input.setInputProcessor(multiplexer);
    }
}