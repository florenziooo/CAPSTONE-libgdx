package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import io.mygame.common.GameManager;
import io.mygame.common.SoundManager;
import io.mygame.datahandler.GameDataHandler;

import java.io.File;

public class MainMenu extends WildCatScreen {
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    private Skin redSkin;
    private Skin yellowSkin;
    private GameManager gameManager;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void show () {
        background = new Texture("images/main_menu_bg.png");
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        gameManager = GameManager.getInstance();

        sound = new SoundManager.Builder()
            .setbgMusic("sound/music/the_secret_spring_loopable.mp3")
            .build();

        yellowSkin = new Skin(Gdx.files.internal("skins/main_menu_button/yellow/yellow.json"));
        redSkin = new Skin(Gdx.files.internal("skins/main_menu_button/red/red.json"));

        File dataDirectory = new File(GameDataHandler.dataDirectory);

        Table table = new Table();
        table.setY(-250);
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        TextButton startButton = new TextButton("START GAME", yellowSkin);
        table.add(startButton).space(15f);
        table.row();

        TextButton loadButton = new TextButton("LOAD GAME", yellowSkin);
        if(!dataDirectory.exists()) {
            loadButton.setDisabled(true);
        }
        table.add(loadButton).space(15f);
        table.row();

        TextButton exitButton = new TextButton("EXIT GAME", redSkin);
        table.add(exitButton).space(15f);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound.addSound("click");
                gameManager.resetDefaultValues();
                GameDataHandler.saveGameData();
                changeScreen(new GameScreen(game, sound));
            }
        });

        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound.addSound("click");
                GameDataHandler.loadGameData();
                changeScreen(new GameScreen(game, sound));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
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
    public void changeScreen(Screen screen) {
        game.setScreen(screen);
    }
}
