package com.gnudios.libgdx.view;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public abstract class AbstractGameScreen implements Screen {

    protected DirectedGame game;

    public AbstractGameScreen(DirectedGame game) {
        this.game = game;
    }

    @Override
    public abstract void render(float deltaTime);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void show();

    @Override
    public abstract void hide();

    @Override
    public abstract void pause();

    public abstract InputProcessor getInputProcessor();

    @Override
    public void resume() {
        // Assets.instance.init(new AssetManager());
    }

    @Override
    public void dispose() {
        // Assets.instance.dispose();
    }

}
