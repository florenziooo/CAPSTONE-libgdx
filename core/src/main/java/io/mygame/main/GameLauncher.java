package io.mygame.main;

import com.badlogic.gdx.Game;
import io.mygame.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameLauncher extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
