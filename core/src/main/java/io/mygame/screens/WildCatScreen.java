package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.mygame.common.GameManager;

public abstract class WildCatScreen implements Screen, ScreenState {
    protected Game game;
    protected GameManager gameManager;

    public WildCatScreen(Game game) {
        this.game = game;
        gameManager = GameManager.getInstance();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    public abstract void render(float delta);

    @Override
    public abstract void show();

    @Override
    public abstract void dispose();

    /**
     * Changes the current screen to a new GameScreen.
     *
     * @param screen the new screen to be displayed
     */
    @Override
    public void changeScreen(Screen screen) {
        game.setScreen(screen);
    }
}
