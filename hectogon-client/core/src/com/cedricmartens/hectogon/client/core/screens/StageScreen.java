package com.cedricmartens.hectogon.client.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.cedricmartens.hectogon.client.core.game.GameManager;
import com.cedricmartens.hectogon.client.core.game.SceneManager;

import static com.cedricmartens.hectogon.client.core.game.Hectogon.HEIGHT;
import static com.cedricmartens.hectogon.client.core.game.Hectogon.WIDTH;

/**
 * Created by Cedric Martens on 2017-04-26.
*/
public abstract class StageScreen implements Screen
{
    private Stage stage;
    private OrthographicCamera camera;
    private SceneManager sceneManager;

    public StageScreen(GameManager gameManager) {
        //TODO camera and stage make up 2 cameras
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        this.sceneManager = gameManager.sceneManager;
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
