package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cedricmartens.hectogon.client.core.game.GameManager;
import com.cedricmartens.hectogon.client.core.ui.UiUtil;

public class MainMenuScreen extends StageScreen
{
    private SpriteBatch batch;

    public MainMenuScreen(final GameManager gameManager) {
        super(gameManager);
        batch = new SpriteBatch();

        Skin skin = UiUtil.getDefaultSkin();
        TextButton txtButtonConnect = new TextButton("Connect", skin);
        txtButtonConnect.addListener(new ClickListener()
             {
                 @Override
                 public void clicked(InputEvent event, float x, float y)
                 {
                     MainMenuScreen.this.getSceneManager().pushScreen(new WorldScreen(gameManager));
                 }
             }
        );

        getStage().addActor(txtButtonConnect);
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
