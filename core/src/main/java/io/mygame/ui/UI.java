package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygame.common.GameManager;
import io.mygame.screens.ScreenState;

/**
 * The UI class is an abstract class that serves as the base for all user interfaces in the game.
 * It handles the common functionality of setting up and rendering the UI elements, including
 * managing the input processor and handling the skin used for UI elements.
 */
public abstract class UI {
    /************ GAME MANAGEMENT ************/
    private final Game game;
    protected final GameManager gameManager;

    /************ UI ELEMENTS ************/
    protected Stage stage;
    protected Skin skin;

    /************ SCREEN MANAGEMENT ************/
    protected ScreenState screenState;


    /**
     * Constructs the UI object with the provided viewport, screen state, and game instance.
     * Initializes the stage, skin, and game manager, and sets the input processor for the stage.
     *
     * @param viewport The viewport for the stage.
     * @param screenState The screen state for tracking the current screen.
     * @param game The game instance.
     */
    public UI(Viewport viewport, ScreenState screenState, Game game) {
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skins/uiSkins/ui.json"));

        gameManager = GameManager.getInstance();

        this.screenState = screenState;
        this.game = game;
    }

    /**
     * Renders the stage by updating and drawing the UI elements.
     */
    public void render() {
        stage.act();
        stage.draw();
    }

    /**
     * Resizes the stage viewport when the window size changes.
     *
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Align to camera
    }

    /**
     * Disposes of the resources used by the UI, including the skin and stage.
     */
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    /**
     * Gets the game instance associated with the UI.
     *
     * @return The game instance.
     */
    public Game getGame() {
        return game;
    }
}

