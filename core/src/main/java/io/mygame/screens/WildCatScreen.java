package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.mygame.common.SoundManager;

public abstract class WildCatScreen implements Screen, ScreenState {
    Game game;
    SoundManager sound;

    public WildCatScreen(Game game) {
        this.game = game;
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public abstract void show();

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
