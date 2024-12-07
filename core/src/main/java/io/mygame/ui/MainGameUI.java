package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.datahandler.GameDataHandler;
import io.mygame.screens.MainMenuScreen;
import io.mygame.screens.ScreenState;

public class MainGameUI extends UI {
    private boolean descriptionViewMode = false;
    private final ScreenViewport screenViewport = new ScreenViewport();

    private CheckBox statsCb;
    private CheckBox mapCb;
    private CheckBox expCb;
    private CheckBox settingCb;
    private TextButton saveBtn;
    private TextButton returnBtn;
    private Button exitBtn;
    private Button menuBtn;
    private Label labelName;
    private Table currentTable;
    private Button ngeBtn;
    private Button rtlBtn;
    private Button gleBtn;
    private Button salBtn;
    private Button libBtn;
    private Button espasyoBtn;
    private Button patioBtn;
    private Button acadBtn;
    private Button elemBtn;
    private Button gymBtn;
    private Button canteenBtn;
    private Button chapelBtn;

    public MainGameUI(ScreenViewport screenViewport, ScreenState screenState, Game game) {
        super(screenViewport, screenState, game);
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

        labelName = new Label(gameManager.getPlayerName(), skin, "header3");
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

        Label label = new Label("Hello, " + gameManager.getPlayerName(), skin, "header");
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
        label = new Label("Building Explored: " + gameManager.getBuildingsFound() + "/12", skin, "body");
        table2.add(label).padBottom(15.0f).align(Align.left);

        table2.row();
        label = new Label("NPCs Interacted: " + gameManager.getNpcFound() + "/6", skin, "body");
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
        table1.padTop(70.0f);
        table1.align(Align.top);

        Label label = new Label("Areas Discovered", skin, "header");
        table1.add(label).padBottom(20.0f);

        table1.row();

        Table table2 = new Table();

        ngeBtn = new Button(skin, "NGE");
        ngeBtn.setDisabled(!gameManager.getAreaFound("NGE"));
        table2.add(ngeBtn).padRight(10.0f).padBottom(20.0f);

        rtlBtn = new Button(skin, "RTL");
        rtlBtn.setDisabled(!gameManager.getAreaFound("RTL"));
        table2.add(rtlBtn).padLeft(10.0f).padRight(10.0f).padBottom(20.0f);

        gleBtn = new Button(skin, "GLE");
        gleBtn.setDisabled(!gameManager.getAreaFound("GLE"));
        table2.add(gleBtn).padLeft(10.0f).padBottom(20.0f);

        table2.row();
        salBtn = new Button(skin, "SAL");
        salBtn.setDisabled(!gameManager.getAreaFound("SAL"));
        table2.add(salBtn).padRight(10.0f).padBottom(20.0f);

        libBtn = new Button(skin, "Library");
        System.out.println(gameManager.getAreaFound("Library"));
        libBtn.setDisabled(!gameManager.getAreaFound("Library"));
        table2.add(libBtn).padLeft(10.0f).padRight(10.0f).padBottom(20.0f);

        espasyoBtn = new Button(skin, "Espasyo");
        espasyoBtn.setDisabled(!gameManager.getAreaFound("Espasyo"));
        table2.add(espasyoBtn).padLeft(10.0f).padBottom(20.0f);

        table2.row();
        patioBtn = new Button(skin, "Patio");
        patioBtn.setDisabled(!gameManager.getAreaFound("Patio"));
        table2.add(patioBtn).padRight(10.0f).padBottom(20.0f);

        acadBtn = new Button(skin, "ACAD");
        acadBtn.setDisabled(!gameManager.getAreaFound("ACAD"));
        table2.add(acadBtn).padLeft(10.0f).padRight(10.0f).padBottom(20.0f);

        elemBtn = new Button(skin, "ELEM");
        elemBtn.setDisabled(!gameManager.getAreaFound("ELEM"));
        table2.add(elemBtn).padLeft(10.0f).padBottom(20.0f);

        table2.row();
        gymBtn = new Button(skin, "Gym");
        gymBtn.setDisabled(!gameManager.getAreaFound("Gym"));
        table2.add(gymBtn).padRight(10.0f);

        canteenBtn = new Button(skin, "Canteen");
        canteenBtn.setDisabled(!gameManager.getAreaFound("Canteen"));
        table2.add(canteenBtn).padLeft(10.0f).padRight(10.0f);

        chapelBtn = new Button(skin, "Chapel");
        chapelBtn.setDisabled(!gameManager.getAreaFound("Chapel"));
        table2.add(chapelBtn).padLeft(10.0f);

        ScrollPane scrollPane = new ScrollPane(table2, skin);
        scrollPane.setFadeScrollBars(false);
        table1.add(scrollPane).align(Align.top).width(1000.0f).height(550.0f);
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
        explorationButtonListener();
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
                gameManager.getSoundManager().setGlobalVolume(gameManager.getVolume());
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
                gameManager.getSoundManager().addSound("click");
                currentTable.remove();
                statsTable();
            }
        });

        mapCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                currentTable.remove();
                mapTable();
            }
        });

        expCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                currentTable.remove();
                explorationTable();
            }
        });

        settingCb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                currentTable.remove();
                settingsTable();
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                menuBtn.setTouchable(Touchable.enabled);
                currentTable.remove();
            }
        });
    }

    private void settingButtonListener() {
        saveBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                GameDataHandler.saveGameData();
            }
        });

        returnBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameManager.getSoundManager().addSound("click");
                screenState.changeScreen(new MainMenuScreen(getGame()));
            }
        });
    }

    public void keyMenuHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(descriptionViewMode) return;

            gameManager.getSoundManager().addSound("click");
            if(menuBtn.getTouchable() == Touchable.enabled) {
                menuBtn.setTouchable(Touchable.disabled);
                statsTable();
            } else {
                menuBtn.setTouchable(Touchable.enabled);
                currentTable.remove();
            }
        }
    }

    private void explorationButtonListener() {
        Button[] buttons = new Button[] {ngeBtn, rtlBtn, gleBtn, salBtn,
            libBtn, espasyoBtn, patioBtn, acadBtn, elemBtn, gymBtn,
            canteenBtn, chapelBtn};

        for(Button button : buttons) {
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gameManager.getSoundManager().addSound("click");

                    if(actor == ngeBtn) {
                        signDescription("NGE");
                    }

                    if(actor == rtlBtn) {
                        signDescription("RTL");
                    }

                    if(actor == gleBtn) {
                        signDescription("GLE");
                    }

                    if (actor == salBtn) {
                        signDescription("SAL");
                    }

                    if(actor == libBtn) {
                        signDescription("Library");
                    }

                    if(actor == espasyoBtn) {
                        signDescription("Espasyo");
                    }

                    if(actor == patioBtn) {
                        signDescription("Patio");
                    }

                    if(actor == acadBtn) {
                        signDescription("ACAD");
                    }

                    if(actor == elemBtn) {
                        signDescription("ELEM");
                    }

                    if(actor == gymBtn) {
                        signDescription("Gym");
                    }

                    if(actor == canteenBtn) {
                        signDescription("Canteen");
                    }

                    if(actor == chapelBtn) {
                        signDescription("Chapel");
                    }
                }
            });
        }
    }

    public void signDescription(String type) {
        Table table = new Table();
        table.setFillParent(true);
        Stack stack = new Stack();
        Image image;

        switch(type) {
            case "NGE":
                image = new Image(skin, "NGEPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "RTL":
                image = new Image(skin, "RTLPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "GLE":
                image = new Image(skin, "GLEPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "SAL":
                image = new Image(skin, "SALPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "Library":
                image = new Image(skin, "LIBPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "Espasyo":
                image = new Image(skin, "EspasyoPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "Patio":
                image = new Image(skin, "LearningPatioPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "ACAD":
                image = new Image(skin, "ACADPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "ELEM":
                image = new Image(skin, "ElemPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "Gym":
                image = new Image(skin, "GymPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "Canteen":
                image = new Image(skin, "CanteenPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            case "Chapel":
                image = new Image(skin, "ChapelPop");
                image.setScaling(Scaling.none);
                stack.addActor(image);
                break;
            default:
                throw new RuntimeException("Sign Description Type does not exist");
        }

        Container container = new Container();
        container.align(Align.topRight);
        container.padRight(20.0f);
        container.padTop(20.0f);

        descriptionViewMode = true;
        statsCb.setTouchable(Touchable.disabled);
        mapCb.setTouchable(Touchable.disabled);
        expCb.setTouchable(Touchable.disabled);
        settingCb.setTouchable(Touchable.disabled);

        Button button = new Button(skin);
        container.setActor(button);
        stack.addActor(container);
        table.add(stack);
        stage.addActor(table);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                descriptionViewMode = false;
                statsCb.setTouchable(Touchable.enabled);
                mapCb.setTouchable(Touchable.enabled);
                expCb.setTouchable(Touchable.enabled);
                settingCb.setTouchable(Touchable.enabled);

                gameManager.getSoundManager().addSound("click");
                table.remove();
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
