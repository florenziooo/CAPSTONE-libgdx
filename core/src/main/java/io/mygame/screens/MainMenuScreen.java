package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.mygame.ui.MainMenuUI;
import io.mygame.ui.UI;

import java.io.File;

public class MainMenuScreen extends WildCatScreen {
    private UI mainMenuUI;

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show () {
        mainMenuUI = new MainMenuUI(this, game);
    }

    @Override
    public void render (float delta) {
        mainMenuUI.render();
    }

    @Override
    public void dispose() {
        mainMenuUI.dispose();
    }
}
