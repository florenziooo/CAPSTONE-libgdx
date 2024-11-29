package io.mygame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygame.common.GameManager;

public abstract class UI {
    protected Stage stage;
    protected Skin skin;
    protected GameManager gameManager = GameManager.getInstance();

    public UI(Viewport viewport) {
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/uiSkins/ui.json"));
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
}

