package io.mygame.screens;

import com.badlogic.gdx.Game;
import io.mygame.ui.IntroGameUI;
import io.mygame.ui.UI;

public class IntroScreen extends WildCatScreen {
    private UI introGameUI;
    public IntroScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        introGameUI = new IntroGameUI(this, game);
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
