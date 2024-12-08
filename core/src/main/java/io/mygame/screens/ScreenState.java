package io.mygame.screens;

import com.badlogic.gdx.Screen;

/**
 * Interface for managing screen transitions in the game.
 * Provides a method for changing the current screen.
 */
public interface ScreenState {
    /**
     * Changes the current screen to the specified screen.
     *
     * @param screen the new screen to transition to
     */
    void changeScreen(Screen screen);
}
