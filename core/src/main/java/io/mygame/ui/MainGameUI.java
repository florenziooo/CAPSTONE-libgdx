package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.common.GameManager;
import io.mygame.common.SoundManager;
import io.mygame.datahandler.GameDataHandler;
import io.mygame.screens.MainMenu;
import io.mygame.screens.ScreenState;

public class MainGameUI extends UI {
    private CheckBox statsCb;
    private CheckBox mapCb;
    private CheckBox expCb;
    private CheckBox settingCb;
    private TextButton saveBtn;
    private TextButton returnBtn;
    private Button exitBtn;
    private Button menuBtn;
    private Table currentTable;
    private SoundManager sound;
    private GameManager gameManager;

    public MainGameUI(ScreenState screenState, Game game, SoundManager sound) {
        super(new ScreenViewport(), screenState, game);
        this.sound = sound;
        gameManager = GameManager.getInstance();

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skins/uiSkins/ui.json"));
        Gdx.input.setInputProcessor(stage);

        playerHUD();
    }

    private void playerHUD() {
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

        Label labelName = new Label(gameManager.getPlayerName() + " (CIT-U)", skin, "header3");
        container.setActor(labelName);
        stack.addActor(container);
        table.add(stack);

        table.row();
        menuBtn = new Button(skin, "open_menu_btn");
        table.add(menuBtn).padLeft(10.0f).padTop(19.0f).align(Align.left).minSize(80.0f);
        stage.addActor(table);

        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                menuBtn.setTouchable(Touchable.disabled);
                statsTable();
            }
        });
    }

    private void statsTable() {
        currentTable = new Table();
        currentTable.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "menu_bg");
        stack.addActor(image);

        Table table1 = new Table();
        table1.padLeft(-79.0f);
        table1.padTop(100.0f);
        table1.align(Align.topLeft);

        statsCb = new CheckBox(null, skin, "stats_cb");
        statsCb.setChecked(true);
        statsCb.setTouchable(Touchable.disabled);
        table1.add(statsCb).padBottom(2.0f);

        table1.row();
        mapCb = new CheckBox(null, skin, "map_cb");
        table1.add(mapCb).padBottom(2.0f);

        table1.row();
        expCb = new CheckBox(null, skin, "exp_cb");
        table1.add(expCb).padBottom(2.0f);

        table1.row();
        settingCb = new CheckBox(null, skin, "settings_cb");
        table1.add(settingCb);
        stack.addActor(table1);

        table1 = new Table();
        table1.padLeft(150.0f);
        table1.padTop(100.0f);
        table1.align(Align.topLeft);

        Label label = new Label("Hello, <NAME>", skin, "header");
        table1.add(label).padBottom(40.0f).align(Align.left).colspan(2);

        table1.row();
        image = new Image(skin, "avatar_frame");
        table1.add(image).padLeft(50.0f).padRight(60.0f);

        Table table2 = new Table();

        label = new Label("School: CIT-U", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("ID: 12-3456-789", skin, "body");
        table2.add(label).padBottom(40.0f).align(Align.left);

        table2.row();
        label = new Label("Student Exploration", skin, "header2");
        table2.add(label).padBottom(20.0f).align(Align.left);

        table2.row();
        label = new Label("Building Explored: 0/20", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("NPCs Interacted: 0/20", skin, "body");
        table2.add(label).align(Align.left);
        table1.add(table2).padTop(40.0f).align(Align.top);
        stack.addActor(table1);

        Container container = new Container();
        container.align(Align.topRight);
        container.padRight(35.0f);
        container.padTop(20.0f);

        exitBtn = new Button(skin);
        container.setActor(exitBtn);
        stack.addActor(container);
        currentTable.add(stack);
        stage.addActor(currentTable);

        buttonListener();
    }

    private void mapTable() {
        currentTable = new Table();
        currentTable.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "menu_bg");
        stack.addActor(image);

        Table table1 = new Table();
        table1.padLeft(-79.0f);
        table1.padTop(100.0f);
        table1.align(Align.topLeft);

        statsCb = new CheckBox(null, skin, "stats_cb");
        table1.add(statsCb).padBottom(2.0f);

        table1.row();
        mapCb = new CheckBox(null, skin, "map_cb");
        mapCb.setTouchable(Touchable.disabled);
        mapCb.setChecked(true);
        table1.add(mapCb).padBottom(2.0f);

        table1.row();
        expCb = new CheckBox(null, skin, "exp_cb");
        table1.add(expCb).padBottom(2.0f);

        table1.row();
        settingCb = new CheckBox(null, skin, "settings_cb");
        table1.add(settingCb);
        stack.addActor(table1);

        table1 = new Table();
        table1.setTouchable(Touchable.enabled);
        table1.padTop(60.0f);
        table1.align(Align.top);

        Label label = new Label("CIT-U Campus Map", skin, "header");
        table1.add(label);

        table1.row();
        image = new Image(skin, "map_frame2");
        table1.add(image);
        stack.addActor(table1);

        Container container = new Container();
        container.align(Align.topRight);
        container.padRight(35.0f);
        container.padTop(20.0f);

        exitBtn = new Button(skin);
        container.setActor(exitBtn);
        stack.addActor(container);
        currentTable.add(stack);
        stage.addActor(currentTable);

        buttonListener();
    }

    private void explorationTable() {
        currentTable = new Table();
        currentTable.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "menu_bg");
        stack.addActor(image);

        Table table1 = new Table();
        table1.padLeft(-79.0f);
        table1.padTop(100.0f);
        table1.align(Align.topLeft);

        statsCb = new CheckBox(null, skin, "stats_cb");
        table1.add(statsCb).padBottom(2.0f);

        table1.row();
        mapCb = new CheckBox(null, skin, "map_cb");
        table1.add(mapCb).padBottom(2.0f);

        table1.row();
        expCb = new CheckBox(null, skin, "exp_cb");
        expCb.setTouchable(Touchable.disabled);
        expCb.setChecked(true);
        table1.add(expCb).padBottom(2.0f);

        table1.row();
        settingCb = new CheckBox(null, skin, "settings_cb");
        table1.add(settingCb);
        stack.addActor(table1);

        table1 = new Table();
        table1.setTouchable(Touchable.enabled);
        table1.padTop(60.0f);
        table1.align(Align.top);

        Label label = new Label("Areas Discovered", skin, "header");
        table1.add(label).padBottom(50.0f);

        table1.row();
        Table table2 = new Table();

        ImageButton imageButton = new ImageButton(skin);
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "RTL");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "GLE");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "SAL");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        table2.row();
        imageButton = new ImageButton(skin, "LRAC");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "ESPACIO");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "PATIO");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "ACAD");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        table2.row();
        imageButton = new ImageButton(skin, "ELEM");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "GYM");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "CANTEEN");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);

        imageButton = new ImageButton(skin, "CHAPEL");
        table2.add(imageButton).padLeft(20.0f).padRight(20.0f).padBottom(20.0f);
        table1.add(table2).maxHeight(600.0f);
        stack.addActor(table1);

        Container container = new Container();
        container.align(Align.topRight);
        container.padRight(35.0f);
        container.padTop(20.0f);

        exitBtn = new Button(skin);
        container.setActor(exitBtn);
        stack.addActor(container);
        currentTable.add(stack);
        stage.addActor(currentTable);

        buttonListener();
    }

    private void settingsTable() {
        currentTable = new Table();
        currentTable.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "menu_bg");
        stack.addActor(image);

        Table table1 = new Table();
        table1.padLeft(-79.0f);
        table1.padTop(100.0f);
        table1.align(Align.topLeft);

        statsCb = new CheckBox(null, skin, "stats_cb");
        table1.add(statsCb).padBottom(2.0f);

        table1.row();
        mapCb = new CheckBox(null, skin, "map_cb");
        table1.add(mapCb).padBottom(2.0f);

        table1.row();
        expCb = new CheckBox(null, skin, "exp_cb");
        table1.add(expCb).padBottom(2.0f);

        table1.row();
        settingCb = new CheckBox(null, skin, "settings_cb");
        settingCb.setTouchable(Touchable.disabled);
        settingCb.setChecked(true);
        table1.add(settingCb);
        stack.addActor(table1);

        table1 = new Table();
        table1.setTouchable(Touchable.enabled);
        table1.padTop(80.0f);
        table1.align(Align.top);

        Label label = new Label("Settings", skin, "header");
        table1.add(label).spaceBottom(60.0f).colspan(2);

        table1.row();
        Table table2 = new Table();

        label = new Label("Volume", skin, "body");
        label.setAlignment(Align.center);
        table2.add(label).padBottom(15.0f).growX();

        table2.row();
        Slider slider = new Slider(0.0f, 1.0f, 0.1f, false, skin, "default-horizontal");
        slider.setValue(gameManager.getVolume());

        slider.setAnimateInterpolation(Interpolation.smooth);
        slider.setVisualInterpolation(Interpolation.smooth);

        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameManager.setVolume(slider.getValue());
                // Update background music volume
                sound.setGlobalVolume(gameManager.getVolume());
            }
        });

        table2.add(slider).padBottom(50.0f).fillX();

        table2.row();
        saveBtn = new TextButton("Save Game", skin);
        table2.add(saveBtn).padBottom(30.0f).fillX().height(70.0f);

        table2.row();
        returnBtn = new TextButton("Return Main", skin);
        table2.add(returnBtn).fillX().height(70.0f);
        table1.add(table2).size(400.0f);

        table2 = new Table();

        label = new Label("Key Bindings:", skin, "body");
        table2.add(label).padBottom(40.0f).align(Align.left);

        table2.row();
        label = new Label("W - Move UP", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("A - Move Left", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("S - Move Down", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("D - Move Right", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("E - Interact", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("Q - Open/Exit Inventory", skin, "body");
        table2.add(label).align(Align.left);
        table1.add(table2).spaceLeft(80.0f);
        stack.addActor(table1);

        Container container = new Container();
        container.align(Align.topRight);
        container.padRight(35.0f);
        container.padTop(20.0f);

        exitBtn = new Button(skin);
        container.setActor(exitBtn);
        stack.addActor(container);
        currentTable.add(stack);
        stage.addActor(currentTable);

        buttonListener();
        settingButtonListener();
    }

    private void buttonListener() {
        statsCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                currentTable.remove();
                statsTable();
            }
        });

        mapCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                currentTable.remove();
                mapTable();
            }
        });

        expCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                currentTable.remove();
                explorationTable();
            }
        });

        settingCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                currentTable.remove();
                settingsTable();
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                menuBtn.setTouchable(Touchable.enabled);
                currentTable.remove();
            }
        });
    }

    private void settingButtonListener() {
        saveBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                GameDataHandler.saveGameData();
            }
        });

        returnBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                screenState.changeScreen(new MainMenu(getGame()));
            }
        });
    }

    public void keyMenuHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(menuBtn.getTouchable() == Touchable.enabled) {
                menuBtn.setTouchable(Touchable.disabled);
                statsTable();
            } else {
                menuBtn.setTouchable(Touchable.enabled);
                currentTable.remove();
            }
        }
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
