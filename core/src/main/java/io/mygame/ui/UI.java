package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygame.common.GameManager;
import io.mygame.screens.ScreenState;
import io.mygame.common.SoundManager;


public abstract class UI {
    private final Game game;
    protected Stage stage;
    protected Skin skin;
    protected ScreenState screenState;
    protected final GameManager gameManager;
    protected SoundManager sound;

    public UI(Viewport viewport, ScreenState screenState, Game game, SoundManager sound) {
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skins/uiSkins/ui.json"));

        gameManager = GameManager.getInstance();

        this.screenState = screenState;
        this.game = game;
        this.sound = sound;
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Align to camera
    }

    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    public Game getGame() {
        return game;
    }
}

