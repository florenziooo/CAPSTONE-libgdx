package io.mygame.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerHUD extends UI {
    private Table root;
    private Label playerName;
    private Button menuButton;

    public PlayerHUD() {
        super(new ScreenViewport());

        playerName = new Label("Hello, " + gameManager.getPlayerName(), skin, "default");
        menuButton = new Button(skin, "open_menu_btn");

        root = new Table();
        root.top().right();
        root.setFillParent(true);
        root.pad(10);

        root.add(menuButton).padBottom(10);
        root.row();
        root.add(playerName).padBottom(10);

        root.setDebug(true);
        stage.addActor(root);

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                playerName.setText("Hello World");
            }
        });
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
