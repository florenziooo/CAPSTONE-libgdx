package io.mygame.screens;

import com.badlogic.gdx.Game;
import io.mygame.ui.IntroGameUI;
import io.mygame.ui.UI;

/**
 * Screen that displays the introductory user interface.
 */
public class IntroScreen extends WildCatScreen {
    /************ USER INTERFACE ************/
    private UI introGameUI;

    /**
     * Constructor for the IntroScreen class.
     *
     * @param game the main game instance
     */
    public IntroScreen(Game game) {
        super(game);
    }

    /**
     * Called when the screen is shown. Initializes the intro UI.
     */
    @Override
    public void show() {
        introGameUI = new IntroGameUI(this, game);
    }

    /**
     * Disposes of resources used by the intro UI.
     */
    @Override
    public void dispose() {
        introGameUI.dispose();
    }

    /**
     * Renders the intro UI on the screen.
     *
     * @param delta the time in seconds since the last frame
     */
    @Override
    public void render(float delta) {
        introGameUI.render();
    }
}
