package com.cedricmartens.hectogon.client.core.graphics.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cedricmartens.commons.entities.animal.Animal;
import com.cedricmartens.commons.entities.animal.AnimalState;

public abstract class AnimalAnimation<T extends Animal> extends AnimationSequence<TextureRegion>
{
    protected T animal;

    @Override
    public TextureRegion getCurrentFrame() {
        if(this.animal.getAnimalState() == AnimalState.IDLE)
            return getFrameAtIndex(0);
        return super.getCurrentFrame();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch, animal.getPosition().x - getCurrentFrame().getRegionWidth()/2,
                animal.getPosition().y - getCurrentFrame().getRegionHeight()/2);
    }
}
