package com.cedricmartens.hectogon.client.core.graphics.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cedricmartens.commons.entities.Rabbit;
import com.cedricmartens.commons.util.FuzzyDirection;
import com.cedricmartens.commons.util.MathUtil;

/**
 * Created by Cedric Martens on 2017-12-08.
 */
public class RabbitAnimation extends AnimationSequence<TextureRegion>
{
    private Animation<TextureRegion> bunnyUp;
    private Animation<TextureRegion> bunnyDown;
    private Animation<TextureRegion> bunnyLeft;
    private Animation<TextureRegion> bunnyRight;
    private Rabbit rabbit;
    public RabbitAnimation(Rabbit rabbit) {
        super();
        Animator animator = Animator.getAnimator();
        this.rabbit = rabbit;
        this.bunnyUp = animator.get("bunny_up");
        this.bunnyDown = animator.get("bunny_down");
        this.bunnyLeft = animator.get("bunny_left");
        this.bunnyRight = animator.get("bunny_right");
    }

    @Override
    protected void setAnimationFromFuzzyDirection(FuzzyDirection fuzzyDirection)
    {
        switch (fuzzyDirection) {
            case RIGHT:
                setAnimation(this.bunnyRight);
                break;
            case UP:
                setAnimation(this.bunnyUp);
                break;
            case LEFT:
                setAnimation(this.bunnyLeft);
                break;
            case DOWN:
                setAnimation(this.bunnyDown);
                break;
        }
    }

    @Override
    public void update(float delta) {
        setAnimationFromFuzzyDirection(MathUtil.getFuzzyDirection(rabbit.getDirection().angleRad()));
        super.update(delta);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch, rabbit.getPosition().x - getCurrentFrame().getRegionWidth()/2,
                rabbit.getPosition().y - getCurrentFrame().getRegionHeight()/2);
    }
}
