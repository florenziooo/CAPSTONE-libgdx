package io.mygame.screens;

import com.badlogic.gdx.Game;
import io.mygame.common.SoundManager;
import io.mygame.ui.MainMenuUI;
import io.mygame.ui.UI;

public class MainMenuScreen extends WildCatScreen {
    private UI mainMenuUI;

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show () {
        sound = new SoundManager.Builder()
            .setbgMusic("sound/music/the_secret_spring_loopable.mp3")
            .build();

        mainMenuUI = new MainMenuUI(this, game, sound);
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
