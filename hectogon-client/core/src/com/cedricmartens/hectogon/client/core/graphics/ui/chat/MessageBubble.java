package com.cedricmartens.hectogon.client.core.graphics.ui.chat;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cedricmartens.commons.entities.Entity;

/**
 * Created by Cedric Martens on 2017-11-09.
 */
public class MessageBubble extends Label
{
    private final static int BUBBLE_TIME = 5000; //ms
    private Entity target;
    private long creationTime;

    public MessageBubble(Entity target, CharSequence text, Skin skin)
    {
        super(text, skin);
        this.target = target;
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        float x = target.getPosition().x;
        float y = target.getPosition().y;

        setX(x);
        setY(y + 50);

        super.draw(batch, parentAlpha);
    }

    public boolean shouldRemove()
    {
        return System.currentTimeMillis() > creationTime + BUBBLE_TIME;
    }

    public Entity getTarget()
    {
        return target;
    }
}
