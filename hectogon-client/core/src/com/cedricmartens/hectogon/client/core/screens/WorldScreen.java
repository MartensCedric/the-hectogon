package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.chat.Message;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.entities.Entity;
import com.cedricmartens.commons.networking.InvalidPacketDataException;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.networking.actions.PacketCompetitorMovement;
import com.cedricmartens.commons.networking.actions.PacketPositionCorrection;
import com.cedricmartens.commons.networking.competitor.PacketCompetitor;
import com.cedricmartens.commons.networking.competitor.PacketCompetitorJoin;
import com.cedricmartens.commons.networking.competitor.PacketDeath;
import com.cedricmartens.commons.networking.inventory.PacketDropItem;
import com.cedricmartens.commons.networking.inventory.PacketInventory;
import com.cedricmartens.commons.networking.inventory.PacketLoot;
import com.cedricmartens.commons.storage.Chest;
import com.cedricmartens.commons.storage.Lootbag;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.hectogon.client.core.game.Animator;
import com.cedricmartens.hectogon.client.core.game.manager.GameManager;
import com.cedricmartens.hectogon.client.core.game.player.DefaultInput;
import com.cedricmartens.hectogon.client.core.game.player.InputService;
import com.cedricmartens.hectogon.client.core.game.player.NetworkMovementListener;
import com.cedricmartens.hectogon.client.core.game.player.Player;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;
import com.cedricmartens.hectogon.client.core.ui.chat.Chat;
import com.cedricmartens.hectogon.client.core.ui.chat.ChatInput;
import com.cedricmartens.hectogon.client.core.ui.chat.OnFocusChange;
import com.cedricmartens.hectogon.client.core.ui.chat.OnSend;
import com.cedricmartens.hectogon.client.core.ui.inventory.DropListener;
import com.cedricmartens.hectogon.client.core.ui.inventory.InventoryUI;
import com.cedricmartens.hectogon.client.core.util.ServiceUtil;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;
import com.cedricmartens.hectogon.client.core.world.Map;
import com.cedricmartens.hectogon.client.core.world.StartStone;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.cedricmartens.hectogon.client.core.game.Hectogon.HEIGHT;
import static com.cedricmartens.hectogon.client.core.game.Hectogon.WIDTH;
import static java.lang.Math.PI;

public class WorldScreen extends StageScreen {

    private Map map;
    private SpriteBatch batch, uiBatch;
    private ShapeRenderer debugRenderer;
    private OrthographicCamera worldCamera;
    private AssetManager assetManager;
    private Socket socket;
    private InventoryUI inventoryUI;
    private Inventory playerInv;
    private Chest chest;
    private List<Competitor> competitors;
    private List<Entity> decorations;
    private List<Lootbag> drops;
    private Player player;
    private float elapsedTime = 0f;

    public WorldScreen(GameManager gameManager)
    {
        super(gameManager);

        this.socket = gameManager.socket;
        this.competitors = new ArrayList<>();
        this.decorations = new ArrayList<>();
        this.drops = new ArrayList<>();

        for(int i = 0; i < 100; i++)
        {
            float x = Math.round(2500 * Math.cos((PI * i) / (100 / 2)));
            float y = Math.round(2500 * Math.sin((PI * i) / (100 / 2)));
            this.decorations.add(new StartStone(x, y));
        }
        this.player = new Player(null, null,
                new NetworkMovementListener(socket));
        this.debugRenderer = new ShapeRenderer();
        this.debugRenderer.setAutoShapeType(true);
        this.chest = new Chest(12);
        this.chest.setPosition(new Point(0, 0));
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
        inventoryUI = new InventoryUI(playerInv);
        Texture textureInventory = gameManager.assetManager.get("ui/inventory.png", Texture.class);
        Drawable drawableInventory = new TextureRegionDrawable(new TextureRegion(
                textureInventory));
        inventoryUI.setBackground(drawableInventory);
        inventoryUI.setWidth(textureInventory.getWidth() * 2);
        inventoryUI.setHeight(textureInventory.getHeight() * 2);
        inventoryUI.setX(WIDTH - textureInventory.getWidth() * 2);
        inventoryUI.setY(100);
        inventoryUI.setDropListener((item, qty) -> {
            PacketDropItem packetDropItem = new PacketDropItem();
            packetDropItem.setItem(item);
            packetDropItem.setQty(qty);
            try {
                Packet.writeHeader(PacketDropItem.class, socket.getOutputStream());
                packetDropItem.writeTo(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        String[] icons = new String[]{"icons/backpack.png", "items/steel_sword.png"};

        for(int i = 0; i < icons.length; i++)
        {
            Texture txtFrame = assetManager.get("ui/frame.png", Texture.class);
            Texture txtChild = assetManager.get(icons[i], Texture.class);

            Image image = new Image(txtFrame);
            Image child = new Image(txtChild);

            image.setWidth(image.getWidth());
            image.setHeight(image.getHeight());

            child.setWidth(child.getWidth());
            child.setHeight(child.getHeight());

            image.setX(1500 + i * image.getWidth());
            child.setX(1500 + i * image.getWidth() + 8);
            child.setY(8);
            getStage().addActor(image);
            getStage().addActor(child);
        }

        inventoryUI.setDebug(true);
        inventoryUI.debugTable();

        getStage().addActor(inventoryUI);

        final Chat chat = new Chat(UiUtil.getHectogonSkin());
        chat.setWidth(WIDTH / 2.5f);
        chat.setHeight(300);
        chat.setX(15);
        chat.setY(50);
        getStage().addActor(chat);

        final ChatInput chatInput = new ChatInput("", UiUtil.getChatSkin());
        chatInput.setWidth(WIDTH / 2.5f);
        chatInput.setX(15);
        chatInput.setY(15);
        chatInput.setOnSendAction(() -> {
            if (chatInput.getText().length() > 0) {
                PacketChat packetChat = new PacketChat(chatInput.getText(), player.getUser().getUserId(), ChatType.LOCAL);
                try {
                    Packet.writeHeader(PacketChat.class, socket.getOutputStream());
                    packetChat.writeTo(socket.getOutputStream());
                    Message message = new Message();
                    message.setSender(player.getUser());
                    message.setContents(chatInput.getText());
                    message.setChatType(ChatType.GLOBAL);

                    chat.addMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        chatInput.setOnFocusChange(isFocus -> {
            if(player != null)
            {
                player.setInputEnabled(!isFocus);
            }
        });

        getStage().addActor(chatInput);

        getStage().getRoot().addCaptureListener(new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                InputService inputService = ServiceUtil.getServiceUtil().getInputService();
                if(inputService.toggleChatInput(character))
                {
                    getStage().setKeyboardFocus(chatInput);
                    return true;
                }else if(inputService.toggleChatBox(character) &&
                        getStage().getKeyboardFocus() != chatInput)
                {
                    chat.toggleChatBox();
                    return true;
                }

                return super.keyTyped(event, character);
            }
        });

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Packet packet = Packet.readHeader(socket.getInputStream());
                    packet.readFrom(socket.getInputStream());
                    System.out.println(packet.getClass().getSimpleName());
                    if (packet instanceof PacketChat) {
                        PacketChat packetChat = (PacketChat) packet;
                        User u = WorldScreen.this.getCompetitorById(packetChat.getSenderId()).getUser();
                        Message m = new Message();
                        m.setSender(u);
                        m.setContents(packetChat.getMessage());
                        m.setChatType(packetChat.getChatType());

                        chat.addMessage(m);

                    }else if(packet instanceof PacketCompetitor)
                    {
                        PacketCompetitor packetCompetitor = (PacketCompetitor)packet;
                        competitors.add(packetCompetitor.getCompetitor());
                    }else if(packet instanceof PacketCompetitorJoin)
                    {
                        PacketCompetitorJoin packetCompetitorJoin = (PacketCompetitorJoin) packet;
                        Competitor competitor = packetCompetitorJoin.getCompetitor();

                        if(competitors.size() == 0)
                        {
                            player.setUser(competitor.getUser());
                            player.setPosition(competitor.getPosition());
                            competitors.add(player);
                        }else{
                            competitors.add(competitor);
                        }
                    }else if(packet instanceof PacketCompetitorMovement)
                    {
                        PacketCompetitorMovement competitorMovement = (PacketCompetitorMovement) packet;
                        Competitor competitor = getCompetitorById(competitorMovement.getUserId());
                        competitor.processMovement(competitorMovement.getMovementAction());
                    }else if(packet instanceof PacketLoot)
                    {
                        PacketLoot pl = (PacketLoot)packet;
                        Lootbag lootbag = new Lootbag(pl.getPoint().x, pl.getPoint().y, pl.getInventory());
                        drops.add(lootbag);
                    }
                    else if (packet instanceof PacketInventory)
                    {
                        PacketInventory packetInventory = (PacketInventory) packet;
                        inventoryUI.setInventory(packetInventory.getInventory());
                    }else if(packet instanceof PacketPositionCorrection)
                    {
                        PacketPositionCorrection ppc = (PacketPositionCorrection) packet;
                        Competitor c = getCompetitorById(ppc.getUserId());
                        c.correctPosition(ppc.getPosition(), ppc.getTime());
                    }else if(packet instanceof PacketDeath)
                    {
                        final PacketDeath packetDeath = (PacketDeath) packet;
                        if(player.getUser().getUserId() == ((PacketDeath) packet).getUserId())
                        {
                            Thread.currentThread().interrupt();
                            Gdx.app.postRunnable(() -> gameManager.sceneManager.popScreen());

                        }
                        competitors.removeIf(p -> p.getUser().getUserId() == packetDeath.getUserId());
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
        }, "Client-Listener").start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.elapsedTime += delta;
        if(!playerHasConnected())
            return;

        for(Competitor competitor : competitors)
            competitor.move(delta);

        Texture playerDummy = assetManager.get("character/dummy.png", Texture.class);
        float playerOffsetX = playerDummy.getWidth()/2;
        float playerOffsetY = playerDummy.getHeight()/2;

        worldCamera.position.x = player.getPosition().x;
        worldCamera.position.y = player.getPosition().y + playerOffsetY;
        worldCamera.update();

        batch.setProjectionMatrix(this.worldCamera.combined);
        this.batch.begin();
        map.render(batch, player.getPosition().x, player.getPosition().y, 500);

        for(Entity e : decorations)
        {
            if(e.getPosition().distanceBetweenPoint(player.getPosition()) < 500)
            {
                if(e instanceof StartStone)
                {
                    Texture texStartStone = assetManager.get("misc/startstone.png", Texture.class);
                    batch.draw(texStartStone,
                            e.getPosition().x - texStartStone.getWidth()/2,
                            e.getPosition().y - texStartStone.getHeight()/2);
                }
            }
        }

        batch.draw(assetManager.get("interactive/chest.png", Texture.class), chest.getPosition().x,chest.getPosition().y);

        Texture txtLb = assetManager.get("interactive/lootbag.png", Texture.class);
        float lbOffsetX = txtLb.getWidth()/2;
        float lbOffsetY = txtLb.getHeight()/2;

        for(Lootbag l : drops)
            batch.draw(txtLb, l.getPosition().x - lbOffsetX, l.getPosition().y - lbOffsetY);


        for(Competitor c : competitors)
            batch.draw(playerDummy, c.getPosition().x - playerOffsetX, c.getPosition().y);

        this.batch.end();

        this.debugRenderer.begin();
        this.debugRenderer.setProjectionMatrix(getStage().getCamera().combined);
        this.debugRenderer.line(0, HEIGHT/2, WIDTH, HEIGHT/2);
        this.debugRenderer.line(WIDTH/2, 0, WIDTH/2, HEIGHT);
        this.debugRenderer.setProjectionMatrix(worldCamera.combined);
        this.debugRenderer.line(player.getPosition().x - 500, player.getPosition().y, player.getPosition().x + 500, player.getPosition().y);
        this.debugRenderer.rect(chest.getPosition().x, chest.getPosition().y, 64, 64);
        this.debugRenderer.rect(player.getPosition().x - playerOffsetX, player.getPosition().y, playerDummy.getWidth(), playerDummy.getHeight());
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
            if(this.inventoryUI.getSelectedAmount() > 1)
                textureUtil.getFont().draw(uiBatch,
                        Integer.toString(inventoryUI.getSelectedAmount()),
                        pos.x + textureItem.getWidth()/2, pos.y - textureItem.getHeight()/2);
            this.uiBatch.end();
        }
    }

    private Competitor getCompetitorById(int id)
    {
        for (Competitor competitor : competitors) {
            if (competitor.getUser().getUserId() == id) {
                return competitor;
            }
        }

        throw new IllegalStateException("Looking for Id:" + id);
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
        multiplexer.addProcessor(this.getStage());
        multiplexer.addProcessor(player);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private boolean playerHasConnected()
    {
        return player.getUser() != null;
    }
}