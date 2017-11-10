package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends StageScreen
{
    private SpriteBatch batch;

    public MainMenuScreen() {
        super();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
