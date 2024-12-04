package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.datahandler.GameDataHandler;
import io.mygame.screens.GameScreen;
import io.mygame.screens.IntroScreen;
import io.mygame.screens.ScreenState;

import java.io.File;

public class MainMenuUI extends UI {
    private SpriteBatch batch;
    private Texture background;
    private TextButton startBtn;
    private TextButton loadBtn;
    private TextButton exitBtn;

    public MainMenuUI(ScreenState screenState, Game game) {
        super(new ScreenViewport(), screenState, game);

        mainMenuUI();
    }

    private void mainMenuUI() {
        background = new Texture("images/main_menu_bg.png");
        batch = new SpriteBatch();

        Skin yellowSkin = new Skin(Gdx.files.internal("skins/main_menu_button/yellow/yellow.json"));
        Skin redSkin = new Skin(Gdx.files.internal("skins/main_menu_button/red/red.json"));

        File dataDirectory = new File(GameDataHandler.dataDirectory);

        Table table = new Table();
        table.setY(-250);
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        startBtn = new TextButton("START GAME", yellowSkin);
        table.add(startBtn).space(15f);
        table.row();

        loadBtn = new TextButton("LOAD GAME", yellowSkin);
        if(!dataDirectory.exists()) {
            loadBtn.setDisabled(true);
        }
        table.add(loadBtn).space(15f);
        table.row();

        exitBtn = new TextButton("EXIT GAME", redSkin);
        table.add(exitBtn).space(15f);

        buttonListener();
    }

    private void buttonListener() {
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundManager.addSound("click");
                gameManager.resetDefaultValues();
                GameDataHandler.saveGameData();
                screenState.changeScreen(new IntroScreen(getGame()));
            }
        });

        loadBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundManager.addSound("click");
                GameDataHandler.loadGameData();
                screenState.changeScreen(new GameScreen(getGame()));
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        batch.dispose();
    }
}
