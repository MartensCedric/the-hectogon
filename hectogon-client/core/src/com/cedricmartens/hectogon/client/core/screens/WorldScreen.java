package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.cedricmartens.hectogon.client.core.game.SceneManager;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;


public class WorldScreen extends StageScreen{

    public WorldScreen(SceneManager sceneManager)
    {
        super(sceneManager);
        TextField chatInput = new TextField("", UiUtil.getChatSkin());
        chatInput.setWidth(400);
        getStage().addActor(chatInput);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
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
}