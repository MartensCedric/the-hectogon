package com.cedricmartens.hectogon.client.core.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.entities.combat.Arrow;
import com.cedricmartens.commons.entities.combat.Projectile;
import com.cedricmartens.commons.util.FuzzyDirection;
import com.cedricmartens.commons.util.MathUtil;
import com.cedricmartens.commons.util.Vector2;
import com.cedricmartens.hectogon.client.core.game.player.InputService;
import com.cedricmartens.hectogon.client.core.util.ServiceUtil;

import java.util.ArrayList;
import java.util.List;

public class CombatManager
{
    private GameManager gameManager;
    private List<Projectile> projectiles;
    private float cooldown = 0.5f;
    private float currentCooldown = 0;
    private Vector2 rotation = new Vector2();

    public CombatManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
        projectiles = new ArrayList<>();
    }

    public void addProjectile(Projectile projectile)
    {
        synchronized (projectiles) {
            this.projectiles.add(projectile);
        }
    }

    public void draw(Batch batch)
    {
        for (Projectile p : projectiles)
        {
            batch.draw(gameManager.assetManager.get("items/arrow_wood.png", Texture.class),
                    p.getPosition().x, p.getPosition().y);
        }

    }

    public void update(float delta)
    {
        currentCooldown -= delta;
        currentCooldown = currentCooldown < 0 ? 0 : currentCooldown;

        InputService inputService = ServiceUtil.getServiceUtil().getInputService();
        if(currentCooldown == 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            rotation.set(Gdx.input.getX() - Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - Gdx.input.getY());

            currentCooldown = cooldown;
            Projectile arrow = new Arrow(1, rotation.angleRad());
            arrow.setPosition(new Point(gameManager.player.getPosition().x,
                                        gameManager.player.getPosition().y));
            addProjectile(arrow);
        }

        synchronized (projectiles)
        {
            for(Projectile p : projectiles)
                p.update(delta);
        }
    }
}
