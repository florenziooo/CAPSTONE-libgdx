package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.common.GameManager;
import io.mygame.datahandler.GameDataHandler;
import io.mygame.screens.GameScreen;
import io.mygame.screens.MainMenuScreen;
import io.mygame.screens.ScreenState;

public class IntroGameUI extends UI {
    private final GameManager gameManager;
    private TextButton submitBtn;
    private TextButton returnBtn;
    private TextField nameField;

    public IntroGameUI(ScreenState screenState, Game game) {
        super(new ScreenViewport(), screenState, game);
        gameManager = GameManager.getInstance();

        introUI();
    }

    private void introUI() {
        Table table = new Table();
        table.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "IntroScreen");
        image.setScaling(Scaling.fill);
        stack.addActor(image);

        Container container = new Container();
        container.padTop(155.0f);

        Container container1 = new Container();
        container1.width(750.0f);
        container1.padLeft(200.0f);

        nameField = new TextField(null, skin);
        nameField.setMaxLength(20);
        nameField.setMessageText("Type Your Name Here");
        container1.setActor(nameField);
        container.setActor(container1);
        stack.addActor(container);

        container = new Container();
        container.align(Align.bottomRight);
        container.padRight(40.0f);
        container.padBottom(40.0f);

        container1 = new Container();
        container1.width(300.0f);
        container1.height(75.0f);

        returnBtn = new TextButton("Return To Main", skin);
        container1.setActor(returnBtn);
        container.setActor(container1);
        stack.addActor(container);

        container = new Container();
        container.padTop(450.0f);

        container1 = new Container();
        container1.width(300.0f);
        container1.height(75.0f);

        submitBtn = new TextButton("Submit Name", skin);
        submitBtn.setDisabled(true);
        container1.setActor(submitBtn);
        container.setActor(container1);
        stack.addActor(container);
        table.add(stack);
        stage.addActor(table);

        buttonListener();
    }

    private void buttonListener() {
        nameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                submitBtn.setDisabled(nameField.getText().isEmpty());
            }
        });

        submitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameManager.setPlayerName(nameField.getText());
                GameDataHandler.saveGameData();
                screenState.changeScreen(new GameScreen(getGame()));
            }
        });

        returnBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenState.changeScreen(new MainMenuScreen(getGame()));
                System.out.println("Pressed return button");
            }
        });
    }
}
