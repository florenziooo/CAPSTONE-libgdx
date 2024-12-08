package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.datahandler.GameDataHandler;
import io.mygame.screens.GameScreen;
import io.mygame.screens.MainMenuScreen;
import io.mygame.screens.ScreenState;

public class IntroGameUI extends UI {
    private TextButton submitBtn;
    private TextButton returnBtn;
    private TextField nameField;

    public IntroGameUI(ScreenState screenState, Game game) {
        super(new ScreenViewport(), screenState, game);

        introUI();
    }

    private void introUI() {
        Table table = new Table();
        table.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "IntroScreen");
        image.setScaling(Scaling.fill);
        stack.addActor(image);

        Container<Container<TextField>> outerContainer = new Container<>();
        outerContainer.padTop(155.0f);

        Container<TextField> nameFieldContainer = new Container<>();
        nameFieldContainer.width(750.0f);
        nameFieldContainer.padLeft(200.0f);

        nameField = new TextField(null, skin);
        nameField.setMaxLength(20);
        nameField.setMessageText("Type Your Name Here");
        nameFieldContainer.setActor(nameField);
        outerContainer.setActor(nameFieldContainer);
        stack.addActor(outerContainer);

        Container<Container<TextButton>> outerReturnContainer = new Container<>();
        outerReturnContainer.align(Align.bottomRight);
        outerReturnContainer.padRight(40.0f);
        outerReturnContainer.padBottom(40.0f);

        Container<TextButton> returnBtnContainer = new Container<>();
        returnBtnContainer.width(300.0f);
        returnBtnContainer.height(75.0f);

        returnBtn = new TextButton("Return To Main", skin);
        returnBtnContainer.setActor(returnBtn);
        outerReturnContainer.setActor(returnBtnContainer);
        stack.addActor(outerReturnContainer);

        Container<Container<TextButton>> outerSubmitContainer = new Container<>();
        outerSubmitContainer.padTop(450.0f);

        Container<TextButton> submitBtnContainer = new Container<>();
        submitBtnContainer.width(300.0f);
        submitBtnContainer.height(75.0f);

        submitBtn = new TextButton("Submit Name", skin);
        submitBtn.setDisabled(true);
        submitBtnContainer.setActor(submitBtn);
        outerSubmitContainer.setActor(submitBtnContainer);
        stack.addActor(outerSubmitContainer);
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
                gameManager.getSoundManager().addSound("click");
                gameManager.resetDefaultValues();
                gameManager.setPlayerName(nameField.getText());
                GameDataHandler.saveGameData();
                screenState.changeScreen(new GameScreen(getGame()));
            }
        });

        returnBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                screenState.changeScreen(new MainMenuScreen(getGame()));
            }
        });
    }
}
