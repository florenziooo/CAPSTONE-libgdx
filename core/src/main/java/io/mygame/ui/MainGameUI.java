package io.mygame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainGameUI extends UI {
    private CheckBox statsCb;
    private CheckBox mapCb;
    private CheckBox expCb;
    private CheckBox settingCb;
    private Label labelName;

    public MainGameUI() {
        super(new ScreenViewport());
        playerHUD();
    }

    private void playerHUD() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skins/uiSkins/ui.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.top().left();
        table.padTop(15f);
        table.padLeft(15f);
        table.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "player_hud");
        stack.addActor(image);

        Table table1 = new Table();
        stack.addActor(table1);

        Container container = new Container();
        container.align(Align.left);
        container.padLeft(120.0f);
        container.padTop(12.0f);

        labelName = new Label(gameManager.getPlayerName() + " (CIT-U)", skin, "header3");
        container.setActor(labelName);
        stack.addActor(container);
        table.add(stack);

        table.row();
        Button button = new Button(skin, "open_menu_btn");
        table.add(button).padLeft(10.0f).padTop(19.0f).align(Align.left).minSize(80.0f);
        stage.addActor(table);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                emptyMenuUI();
            }
        });
    }

    private void emptyMenuUI() {
        Table table = new Table();
        table.setFillParent(true);

        Stack stack = new Stack();

        Container container = new Container();
        container.padLeft(78.0f);

        Stack stack1 = new Stack();

        Image image = new Image(skin, "menu_bg");
        stack1.addActor(image);

        Table table1 = new Table();
        stack1.addActor(table1);
        container.setActor(stack1);
        stack.addActor(container);

        table1 = new Table();
        table1.padTop(80.0f);
        table1.align(Align.topLeft);

        CheckBox checkBox = new CheckBox(null, skin, "stats_cb");
        table1.add(checkBox);

        table1.row();
        checkBox = new CheckBox(null, skin, "map_cb");
        table1.add(checkBox);

        table1.row();
        checkBox = new CheckBox(null, skin, "exp_cb");
        table1.add(checkBox);

        table1.row();
        checkBox = new CheckBox(null, skin, "settings_cb");
        table1.add(checkBox);
        stack.addActor(table1);
        table.add(stack);
        stage.addActor(table);


//        statsCb.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent changeEvent, Actor actor) {
//
//            }
//        });
    }

    private void statsTable() {

    }

    private void mapTable() {

    }

    private void explorationTable() {

    }

    private void settingsTable() {

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
