package com.cedricmartens.hectogon.client.core.game.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Stack;

public class SceneManager extends Game {
    private Stack<Screen> screens;

    @Override
    public void create() {
        this.screens = new Stack<Screen>();
    }

    public void pushScreen(Screen screen)
    {
        this.screens.push(screen);
        super.setScreen(screen);
    }

    public void popScreen()
    {
        if(this.screens.size() <= 1)
        {
            throw new GdxRuntimeException("Cannot pop the last screen!");
        }

        this.screens.pop();
        this.setScreen(screens.peek());
    }

}
