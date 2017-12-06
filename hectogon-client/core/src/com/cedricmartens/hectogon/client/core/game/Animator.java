package com.cedricmartens.hectogon.client.core.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Animator
{
    private static final int FPS = 60;
    private static Animator animator;

    private Map<String, Animation<TextureRegion>> animationMap;
    public static Animator getAnimator()
    {
        if(animator == null)
        {
            animator = new Animator();
        }
        return animator;
    }

    public void initializeAnimator(AssetManager am)
    {
        animationMap = new HashMap<>();

        TextureRegion[][] ssBunnyLeft = TextureRegion.split(am.get("animals/bunny/bunny-left-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBunnyRight = TextureRegion.split(am.get("animals/bunny/bunny-right-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBunnyDown = TextureRegion.split(am.get("animals/bunny/bunny-down-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBunnyUp = TextureRegion.split(am.get("animals/bunny/bunny-up-animation.png", Texture.class), 32, 32);
        TextureRegion[][] ssBombFuse = TextureRegion.split(am.get("items/bomb_fusing_animation.png", Texture.class), 32, 32);

        Animation<TextureRegion> bunnyUp = new Animation<>(frameDuration(3, 1000), to1DArray(ssBunnyUp));
        bunnyUp.setPlayMode(Animation.PlayMode.LOOP);

        Animation<TextureRegion> bunnyDown = new Animation<>(frameDuration(3, 1000), to1DArray(ssBunnyDown));
        bunnyDown.setPlayMode(Animation.PlayMode.LOOP);

        Animation<TextureRegion> bunnyRight = new Animation<>(frameDuration(3, 1000), to1DArray(ssBunnyRight));
        bunnyRight.setPlayMode(Animation.PlayMode.LOOP);

        Animation<TextureRegion> bunnyLeft = new Animation<>(frameDuration(3, 1000), to1DArray(ssBunnyLeft));
        bunnyLeft.setPlayMode(Animation.PlayMode.LOOP);

        Animation<TextureRegion> bombFuse = new Animation<>(frameDuration(18, 750), to1DArray(ssBombFuse));

        animationMap.put("bunny_up", bunnyUp);
        animationMap.put("bunny_down", bunnyDown);
        animationMap.put("bunny_left", bunnyLeft);
        animationMap.put("bunny_right", bunnyRight);
        animationMap.put("bomb_fuse", bombFuse);
    }

    private static float frameDuration(int frameAmount, float duration)
    {
        return (duration / (float)frameAmount)/1000f;
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

    public Animation<TextureRegion> get(String animationName)
    {
        return animationMap.get(animationName);
    }
}
