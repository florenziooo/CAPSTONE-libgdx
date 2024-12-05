package io.mygame.screens;

import com.badlogic.gdx.Game;
import io.mygame.common.SoundManager;
import io.mygame.ui.IntroGameUI;
import io.mygame.ui.UI;

public class IntroScreen extends WildCatScreen {
    private UI introGameUI;
    public IntroScreen(Game game, SoundManager soundManager) {
        super(game);
        this.sound = soundManager;
    }

    @Override
    public void show() {
        introGameUI = new IntroGameUI(this, game, sound);
    }

    @Override
    public void dispose() {
        introGameUI.dispose();
    }

    @Override
    public void render(float delta) {
        introGameUI.render();
    }
}
