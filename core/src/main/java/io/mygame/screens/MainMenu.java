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
import io.mygame.common.SoundManager;

public class MainMenu extends WildCatScreen {
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private Skin redSkin;
    private Skin yellowSkin;

    private TextButton startButton;
    private TextButton loadButton;
    private TextButton exitButton;

    private float time = 0;



    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void show () {
        background = new Texture("images/main_menu_bg.png");
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        sound = new SoundManager.Builder()
            .setbgMusic("sound/music/the_secret_spring_loopable.mp3")
            .setAmbience("sound/ambience/mild_traffic.mp3")
            .build();

        table = new Table();
        table.setY(-250);
        table.setFillParent(true);
        stage.addActor(table);

        yellowSkin = new Skin(Gdx.files.internal("skins/main_menu_button/yellow/yellow.json"));
        redSkin = new Skin(Gdx.files.internal("skins/main_menu_button/red/red.json"));

        startButton = new TextButton("START GAME", yellowSkin);
        table.add(startButton).space(15f);
        table.row();

        loadButton = new TextButton("LOAD GAME", yellowSkin);
        table.add(loadButton).space(15f);
        table.row();

        exitButton = new TextButton("EXIT GAME", redSkin);
        table.add(exitButton).space(15f);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound.addSound("click");
                dispose();
                game.setScreen(new GameScreen(game, sound));
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
    }
}
