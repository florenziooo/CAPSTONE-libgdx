package io.mygame.main;

import com.badlogic.gdx.Game;
import io.mygame.screens.MainMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameLauncher extends Game {
    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
}
