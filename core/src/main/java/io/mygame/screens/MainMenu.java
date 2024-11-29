package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.ui.UI;

public class MainMenu extends WildCatScreen {
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    private Skin redSkin;
    private Skin yellowSkin;
    private UI ui;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void show () {
        background = new Texture("images/main_menu_bg.png");
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        yellowSkin = new Skin(Gdx.files.internal("skins/main_menu_button/yellow/yellow.json"));
        redSkin = new Skin(Gdx.files.internal("skins/main_menu_button/red/red.json"));

        Table table = new Table();
        table.setY(-250);
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        TextButton startButton = new TextButton("START GAME", yellowSkin);
        table.add(startButton).space(15f);
        table.row();

        TextButton loadButton = new TextButton("LOAD GAME", yellowSkin);
        table.add(loadButton).space(15f);
        table.row();

        TextButton exitButton = new TextButton("EXIT GAME", redSkin);
        table.add(exitButton).space(15f);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(new GameScreen(game));
            }
        });
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        redSkin.dispose();
        yellowSkin.dispose();
    }

    @Override
    public void changeScreen(GameScreen screen) {
        game.setScreen(screen);
    }
}
