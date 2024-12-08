package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

/**
 * The IntroGameUI class represents the user interface for the game's introduction sequence.
 * It handles the initial setup, user input for player name, and navigation to the next screen.
 */
public class IntroGameUI extends UI {
    /************ UI ELEMENTS ************/
    private TextButton submitBtn;
    private TextButton returnBtn;
    private TextField nameField;

    /************ INSTRUCTION TABLES ************/
    private Table instructionTable1;
    private Table instructionTable2;
    private Table instructionTable3;
    private Table introTable;

    /************ INSTRUCTION PROGRESS ************/
    private boolean instructionOneDone;
    private boolean instructionTwoDone;

    /************ LOADING AND TIME TRACKING ************/
    private boolean isLoading;
    private float elapsedTime;

    /**
     * Constructs an IntroGameUI instance with the provided screen state and game reference.
     *
     * @param screenState the current screen state
     * @param game the game instance
     */
    public IntroGameUI(ScreenState screenState, Game game) {
        super(new ScreenViewport(), screenState, game);

        introUI();
    }

    /**
     * Initializes the user interface for the introduction screen, including text fields, buttons, and images.
     */
    private void introUI() {
        introTable = new Table();
        introTable.setFillParent(true);

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
        introTable.add(stack);
        stage.addActor(introTable);

        buttonListener();
    }

    /**
     * Displays the first instruction screen.
     */
    private void instructionUI1() {
        instructionTable1 = new Table();
        instructionTable1.setFillParent(true);

        Image image = new Image(skin, "Instruction Screen");
        image.setScaling(Scaling.fill);
        instructionTable1.add(image);
        stage.addActor(instructionTable1);
    }

    /**
     * Displays the second instruction screen and removes the first instruction screen if present.
     */
    private void instructionUI2() {
        instructionTable2 = new Table();
        instructionTable2.setFillParent(true);

        Image image = new Image(skin, "Instruction Screen2");
        image.setScaling(Scaling.fill);
        instructionTable2.add(image);

        if (instructionTable1 != null) {
            instructionTable1.remove();
        }

        stage.addActor(instructionTable2);
    }

    /**
     * Displays the third instruction screen and removes the second instruction screen if present.
     */
    private void instructionUI3() {
        instructionTable3 = new Table();
        instructionTable3.setFillParent(true);

        Image image = new Image(skin, "Instruction Screen2-1");
        image.setScaling(Scaling.fill);
        instructionTable3.add(image);

        if (instructionTable2 != null) {
            instructionTable2.remove();
        }

        stage.addActor(instructionTable3);
    }

    /**
     * Displays the loading screen and removes the third instruction screen if present.
     */
    private void loadingScreen() {
        Table table = new Table();
        table.setFillParent(true);

        Image image = new Image(skin, "LoadingScreen");
        image.setScaling(Scaling.fill);
        table.add(image);

        if (instructionTable3 != null) {
            instructionTable3.remove();
        }

        stage.addActor(table);
    }

    /**
     * Sets up listeners for the buttons and text field interactions, including name input validation and screen transitions.
     */
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
                introTable.remove();

                instructionUI1();
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

    /**
     * Renders the UI, including handling the transition between instruction screens and loading screens.
     */
    @Override
    public void render() {
        super.render();

        if (isLoading) {
            elapsedTime += Gdx.graphics.getDeltaTime();

            loadingScreen();

            if (elapsedTime >= 0.1f) {
                screenState.changeScreen(new GameScreen(getGame()));
                isLoading = false;
            }
        } else if (introTable.getParent() == null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                if (instructionTwoDone) {
                    isLoading = true;
                    elapsedTime = 0f; // Reset the timer
                    gameManager.getSoundManager().addSound("click");
                } else if (instructionOneDone) {
                    gameManager.getSoundManager().addSound("click");
                    instructionTwoDone = true;
                    instructionUI3();
                } else if (instructionTable1.getParent() != null) {
                    gameManager.getSoundManager().addSound("click");
                    instructionOneDone = true;
                    instructionUI2();
                }
            }
        }
    }
}
