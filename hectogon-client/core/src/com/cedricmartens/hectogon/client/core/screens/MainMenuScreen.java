package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainMenuScreen extends StageScreen
{
    private SpriteBatch batch;

    public MainMenuScreen() {
        super();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Skin skin = new Skin(Gdx.files.internal("skins/message.json"));
        Label labelLong = new Label("This may be the longest message bubble every created because this is a very long message bubble", skin);
        labelLong.setX(200);
        labelLong.setY(500);

        getStage().addActor(labelLong);

        Label labelHi = new Label("Hi", skin);
        labelHi.setX(200);
        labelHi.setY(700);

        getStage().addActor(labelHi);

        Label labelWrapped = new Label("This may be the longest message bubble every created because this is a very long message bubble, BUT THIS TIME IT'S WRAPPED", skin);
        labelWrapped.setWrap(true);
        labelWrapped.setWidth(480);
        labelWrapped.setHeight(labelWrapped.getPrefHeight());
        labelWrapped.setDebug(true);
        labelWrapped.setX(200);
        labelWrapped.setY(100);

        getStage().addActor(labelWrapped);

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
