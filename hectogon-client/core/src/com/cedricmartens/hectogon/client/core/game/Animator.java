package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator
{
    private static final int FPS = 60;
    private static Animator animator;
    private Animation<TextureRegion> bunnyUp;
    private Animation<TextureRegion> bunnyDown;
    private Animation<TextureRegion> bunnyLeft;
    private Animation<TextureRegion> bunnyRight;
    private Animation<TextureRegion> bombFuse;

    public static Animator getAnimator()
    {
        if(animator == null)
            throw new IllegalStateException("Animator not initialized");
        return animator;
    }

    public void initializeAnimator(AssetManager am)
    {
        animator = new Animator();
        TextureRegion[][] ssBunnyLeft = TextureRegion.split(am.get("animals/bunny/bunny-left-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBunnyRight = TextureRegion.split(am.get("animals/bunny/bunny-right-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBunnyDown = TextureRegion.split(am.get("animals/bunny/bunny-down-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBunnyUp = TextureRegion.split(am.get("animals/bunny/bunny-up-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBombFuse = TextureRegion.split(am.get("items/bomb_fusing_animation.png", Texture.class), 32, 32);

        bunnyUp = new Animation<>(frameDuration(3, 500), to1DArray(ssBunnyUp));
        bunnyUp.setPlayMode(Animation.PlayMode.LOOP);

        bunnyDown = new Animation<>(frameDuration(3, 500), to1DArray(ssBunnyDown));
        bunnyDown.setPlayMode(Animation.PlayMode.LOOP);

        bunnyRight = new Animation<>(frameDuration(3, 500), to1DArray(ssBunnyRight));
        bunnyRight.setPlayMode(Animation.PlayMode.LOOP);

        bunnyLeft = new Animation<>(frameDuration(3, 500), to1DArray(ssBunnyLeft));
        bunnyLeft.setPlayMode(Animation.PlayMode.LOOP);

        bombFuse = new Animation<>(frameDuration(18, 750), to1DArray(ssBombFuse));
    }

    private static float frameDuration(int frameAmount, float duration)
    {
        return frameAmount / duration;
    }

    private TextureRegion[] to1DArray(TextureRegion[][] array)
    {
        TextureRegion[] oneD = new TextureRegion[array.length * array[0].length];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                oneD[index++] = array[i][j];
            }
        }
        return oneD;
    }
}
