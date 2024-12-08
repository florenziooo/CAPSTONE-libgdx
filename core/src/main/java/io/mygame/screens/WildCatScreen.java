package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.mygame.common.GameManager;

/**
 * Abstract class representing a screen in the game that implements both the {@link Screen}
 * and {@link ScreenState} interfaces.
 * It provides basic methods for screen management, including resizing, pausing, resuming, and hiding screens.
 * Subclasses must define the behavior for rendering, showing, and disposing of the screen.
 */
public abstract class WildCatScreen implements Screen, ScreenState {
    /************ GAME AND MANAGER ************/
    protected Game game;
    protected GameManager gameManager;

    /**
     * Constructor for initializing the screen with a {@link Game} instance and the {@link GameManager}.
     *
     * @param game the game instance to associate with this screen
     */
    public WildCatScreen(Game game) {
        this.game = game;
        gameManager = GameManager.getInstance();
    }

    /**
     * Resizes the screen when the window is resized.
     *
     * @param width the new width of the screen
     * @param height the new height of the screen
     */
    @Override
    public void resize(int width, int height) {}

    /**
     * Pauses the screen. The default implementation does nothing.
     */
    @Override
    public void pause() {}

    /**
     * Resumes the screen. The default implementation does nothing.
     */
    @Override
    public void resume() {}

    /**
     * Hides the screen. The default implementation does nothing.
     */
    @Override
    public void hide() {}

    /**
     * Renders the screen. Subclasses must provide the implementation for rendering the screen.
     *
     * @param delta the time in seconds since the last frame
     */
    public abstract void render(float delta);

    /**
     * Shows the screen. Subclasses must provide the implementation for showing the screen.
     */
    @Override
    public abstract void show();

    /**
     * Disposes of the screen resources. Subclasses must provide the implementation for disposing of resources.
     */
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
