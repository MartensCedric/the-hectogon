package com.cedricmartens.hectogon.client.core.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.entities.combat.Arrow;
import com.cedricmartens.commons.entities.combat.Projectile;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.combat.PacketProjectile;
import com.cedricmartens.commons.storage.inventory.Item;
import com.cedricmartens.commons.util.FuzzyDirection;
import com.cedricmartens.commons.util.MathUtil;
import com.cedricmartens.commons.util.Vector2;
import com.cedricmartens.hectogon.client.core.game.Criteria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CombatManager
{
    private GameManager gameManager;
    private List<Projectile> projectiles;
    private float cooldown = 0.5f;
    private float currentCooldown = 0;
    private Vector2 rotation = new Vector2();
    private Criteria canFire;

    public CombatManager(GameManager gameManager) {
        this.gameManager = gameManager;
        projectiles = new ArrayList<>();
    }

    public void setFireCriteria(Criteria criteria)
    {
        canFire = criteria;
    }

    public void addProjectile(Projectile projectile)
    {
        synchronized (projectiles) {
            this.projectiles.add(projectile);
        }
    }

    public void draw(Batch batch)
    {
        Texture textArrow = gameManager.assetManager.get("items/arrow_wood.png", Texture.class);
        for (Projectile p : projectiles)
        {
            // TODO Must remove the new TextureRegion
            FuzzyDirection fuzzyDirection = MathUtil.getFuzzyDirection(p.getDirection());
            float degrees = 0;
            switch (fuzzyDirection) {
                case UP:
                    degrees = 90;
                    break;
                case LEFT:
                    degrees = 180;
                    break;
                case DOWN:
                    degrees = 270;
                    break;
            }

            batch.draw(new TextureRegion(textArrow), p.getPosition().x - textArrow.getWidth()/2, p.getPosition().y, textArrow.getWidth()/2,
                    textArrow.getHeight()/2, textArrow.getWidth(), textArrow.getHeight(), 1, 1,
                    degrees);
        }
    }

    public void update(float delta)
    {
        synchronized (projectiles)
        {
            for(int i = 0; i < projectiles.size(); i++)
            {
                Projectile p = projectiles.get(i);
                p.update(delta);
                if(p.getTtl() < 0)
                {
                    projectiles.remove(i);
                    i--;
                }
            }
        }

        currentCooldown -= delta;
        currentCooldown = currentCooldown < 0 ? 0 : currentCooldown;

        if(currentCooldown == 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                && gameManager.player.getInventory().contains(Item.arr_wood)
                && canFire.respectsCriteria())
        {
            Texture textDummy = gameManager.assetManager.get("character/dummy.png", Texture.class);
            rotation.set(Gdx.input.getX() - Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - Gdx.input.getY());

            currentCooldown = cooldown;
            Projectile arrow = new Arrow(1, rotation.angleRad());
            arrow.setPosition(new Point(gameManager.player.getPosition().x + (float)Math.cos(rotation.angleRad()) * 16,
                                        gameManager.player.getPosition().y  + textDummy.getHeight()/2 + (float)Math.sin(rotation.angleRad()) * 16));

            arrow.setSenderId(gameManager.player.getId());
            PacketProjectile packetProjectile = new PacketProjectile();
            packetProjectile.setProjectile(arrow);

            try {
                Packet.writeHeader(PacketProjectile.class, gameManager.socket.getOutputStream());
                packetProjectile.writeTo(gameManager.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameManager.player.getInventory().removeItem(Item.arr_wood);
            projectiles.add(arrow);
        }
    }
}
