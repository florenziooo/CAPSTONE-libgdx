package io.mygame.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import io.mygame.screens.MainMenuScreen;

/**
 * Main entry point of the game that sets a custom cursor and initializes the main menu screen.
 */
public class GameLauncher extends Game {

    /**
     * Sets up a custom cursor and initializes the main menu screen.
     */
    @Override
    public void create() {
        Pixmap cursor = new Pixmap(Gdx.files.internal("cursor/cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(cursor, 4, 4);
        Gdx.graphics.setCursor(customCursor);
        cursor.dispose();

        setScreen(new MainMenuScreen(this));
    }
}
