package com.cedricmartens.hectogon.client.core.graphics.ui.chat;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
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
        if(getStage() != null)
        {
            float x = target.getPosition().x;
            float y = target.getPosition().y;
            Viewport viewport = getStage().getViewport();

            Vector3 v3 = getStage().getCamera().project(
                    new Vector3(x, y, 0),
                    viewport.getScreenX(),
                    viewport.getScreenY(),
                    viewport.getScreenWidth(),
                    viewport.getScreenHeight());


            setX(v3.x);
            setY(v3.y);
            if(shouldRemove())
            {
                for(Actor actor : getStage().getActors())
                {
                    if(actor.equals(this))
                        actor.remove();
                }
            }
        }

        super.draw(batch, parentAlpha);
    }

    public boolean shouldRemove()
    {
        return System.currentTimeMillis() > creationTime + BUBBLE_TIME;
    }
}
