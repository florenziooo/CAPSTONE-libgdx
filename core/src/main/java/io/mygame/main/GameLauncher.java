package io.mygame.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import io.mygame.common.GameManager;
import io.mygame.screens.MainMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameLauncher extends Game {
    @Override
    public void create() {
        Pixmap cursor = new Pixmap(Gdx.files.internal("cursor/cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(cursor, 4, 4);
        Gdx.graphics.setCursor(customCursor);
        cursor.dispose();

        setScreen(new MainMenu(this));
    }
}
