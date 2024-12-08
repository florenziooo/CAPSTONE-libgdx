package io.mygame.screens;

import com.badlogic.gdx.Game;
import io.mygame.common.SoundManager;
import io.mygame.ui.MainMenuUI;
import io.mygame.ui.UI;

/**
 * Screen that displays the main menu of the game.
 */
public class MainMenuScreen extends WildCatScreen {
    /************ USER INTERFACE ************/
    private UI mainMenuUI;

    /**
     * Constructor for the MainMenuScreen class.
     *
     * @param game the main game instance
     */
    public MainMenuScreen(Game game) {
        super(game);
    }

    /**
     * Called when the screen is shown. Initializes the sound manager and the main menu UI.
     */
    @Override
    public void show () {
        gameManager.setSoundManager (new SoundManager.Builder()
            .setbgMusic("sound/music/the_secret_spring_loopable.mp3")
            .build());

        mainMenuUI = new MainMenuUI(this, game);
    }

    /**
     * Renders the main menu UI on the screen.
     *
     * @param delta the time in seconds since the last frame
     */
    @Override
    public void render (float delta) {
        mainMenuUI.render();
    }

    /**
     * Disposes of resources used by the main menu UI.
     */
    @Override
    public void dispose() {
        mainMenuUI.dispose();
    }
}
