package com.cedricmartens.hectogon.client.core.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.inventory.PacketDropItem;
import com.cedricmartens.commons.storage.Lootbag;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.util.MathUtil;
import com.cedricmartens.hectogon.client.core.graphics.ui.inventory.InventoryGround;
import com.cedricmartens.hectogon.client.core.graphics.ui.inventory.InventoryUI;
import com.cedricmartens.hectogon.client.core.graphics.ui.inventory.LootbagNotFoundException;
import com.cedricmartens.hectogon.client.core.util.TextureUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.cedricmartens.hectogon.client.core.game.Hectogon.WIDTH;

public class InventoryManager extends WidgetGroup
{
    private final static float PICK_UP_RANGE = 25;

    private InventoryUI inventoryUI;
    private InventoryGround inventoryGround;
    private GameManager gameManager;
    private List<Lootbag> lootbagList;
    private Lootbag currentLoot;
    private Batch uiBatch;

    public InventoryManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
        this.inventoryUI = new InventoryUI(gameManager.player.getInventory());
        this.lootbagList = new ArrayList<>();
        this.uiBatch = new SpriteBatch();

        AssetManager am = gameManager.assetManager;
        Socket socket = gameManager.socket;
        Texture textureInventory = am.get("ui/inventory.png", Texture.class);

        inventoryUI.setBackground(new TextureRegionDrawable(new TextureRegion(
                textureInventory)));
        inventoryUI.setWidth(textureInventory.getWidth() * 2);
        inventoryUI.setHeight(textureInventory.getHeight() * 2);
        inventoryUI.setX(WIDTH - textureInventory.getWidth() * 2);
        inventoryUI.setY(50);
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

        inventoryGround = new InventoryGround();
        inventoryGround.setVisible(false);
        inventoryGround.setBackground(new TextureRegionDrawable(new TextureRegion(
                textureInventory)));
        inventoryGround.setWidth(textureInventory.getWidth() * 2);
        inventoryGround.setHeight(textureInventory.getHeight() * 2);
        inventoryGround.setX(WIDTH - textureInventory.getWidth() * 2);
        inventoryGround.setY(75 + textureInventory.getHeight() * 2);

        addActor(inventoryUI);
        addActor(inventoryGround);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        if (this.inventoryUI.getSelectedItem() != null)
        {
            this.uiBatch.setProjectionMatrix(getStage().getCamera().combined);
            this.uiBatch.begin();
            TextureUtil textureUtil = TextureUtil.getTextureUtil();
            Texture textureItem = textureUtil.getItemTexture(this.inventoryUI.getSelectedItem());
            Viewport viewPort = getStage().getViewport();
            Vector3 pos = getStage().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0),
                    viewPort.getScreenX(), viewPort.getScreenY(),
                    viewPort.getScreenWidth(), viewPort.getScreenHeight());
            this.uiBatch.draw(textureItem, pos.x - textureItem.getWidth(), pos.y - textureItem.getHeight(),
                    textureItem.getWidth() * 2, textureItem.getHeight() * 2);
            if (this.inventoryUI.getSelectedAmount() > 1)
                textureUtil.getFont().draw(uiBatch,
                        Integer.toString(inventoryUI.getSelectedAmount()),
                        pos.x + textureItem.getWidth() / 2, pos.y - textureItem.getHeight() / 2);
            this.uiBatch.end();
        }
        batch.begin();
    }

    public List<Lootbag> getLootbags()
    {
        return lootbagList;
    }

    public void update(float delta)
    {
        if(currentLoot != null)
        {
            float dis = MathUtil.distanceToPoint(currentLoot.getPosition().x,
                    currentLoot.getPosition().y,
                    gameManager.player.getPosition().x,
                    gameManager.player.getPosition().y);

            if(dis > PICK_UP_RANGE)
            {
                currentLoot = null;
                inventoryGround.setInventory(new Inventory(12));
                inventoryGround.setVisible(false);
            }
        }

        if(!lootbagList.isEmpty())
        {
            Lootbag closestDrop = getClosestDrop();

            if(closestDrop != null &&
                    closestDrop.getInventory() != inventoryGround.getInventory())
            {
                this.currentLoot = closestDrop;
                inventoryGround.setInventory(closestDrop.getInventory());
                inventoryGround.setVisible(true);
            }
        }
    }

    public void addLoot(Lootbag lootbag)
    {
        this.lootbagList.add(lootbag);
    }

    private Lootbag getClosestDrop()
    {
        float bestDis = Float.MAX_VALUE;
        Lootbag drop = null;

        for(Lootbag lootbag : lootbagList)
        {
            float disPlayer = MathUtil.distanceToPoint(
                    lootbag.getPosition().x,
                    lootbag.getPosition().y,
                    gameManager.player.getPosition().x,
                    gameManager.player.getPosition().y);

            if(disPlayer <= Math.min(bestDis, PICK_UP_RANGE))
            {
                drop = lootbag;
                bestDis = disPlayer;
            }
        }
        return drop;
    }

    public void updateLoot(int lootId, Inventory inventory)
    {
        Lootbag lootbag = getLootbagById(lootId);
        lootbag.setInventory(inventory);
    }

    private synchronized Lootbag getLootbagById(int lootId)
    {
        for(Lootbag l : this.lootbagList)
            if(l.getId() == lootId)
                return l;

        throw new LootbagNotFoundException();
    }

    public void setPlayerInventory(Inventory playerInventory) {
        this.inventoryUI.setInventory(playerInventory);
    }

    public void togglePlayerInv()
    {
        this.inventoryUI.setVisible(!inventoryUI.isVisible());
    }

    public void refresh()
    {
        inventoryUI.setInventory(gameManager.player.getInventory());
    }

    public void refreshUI()
    {
        inventoryUI.redraw();
    }

    public boolean hasSelection()
    {
        return this.inventoryUI.getSelectedItem() != null;
    }
}
