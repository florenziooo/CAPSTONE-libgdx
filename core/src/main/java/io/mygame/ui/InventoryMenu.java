package io.mygame.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InventoryMenu extends UI {
    private Table root;
    private Label playerName;
    private Button menuButton;

    public InventoryMenu() {
        super(new ScreenViewport());

        playerName = new Label("Hello", skin, "default");
        menuButton = new Button(skin, "open_menu_btn");
        statsTable();

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

    private void statsTable() {
        Table table = new Table();
        table.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "menu_bg");
        stack.addActor(image);

        Table table1 = new Table();
        table1.setTouchable(Touchable.enabled);
        table1.padLeft(100.0f);
        table1.align(Align.topLeft);

        Label label = new Label("Hello, " + gameManager.getPlayerName(), skin, "header");
        table1.add(label).padTop(90.0f).padBottom(50.0f).align(Align.left).colspan(2);

        table1.add();

        table1.row();
        image = new Image(skin, "avatar_frame");
        image.setScaling(Scaling.fill);
        table1.add(image).padLeft(34.0f);

        Table table2 = new Table();
        table2.align(Align.left);

        label = new Label("School: CIT-U", skin, "body");
        table2.add(label).padTop(42.0f).padBottom(20.0f).align(Align.left);

        table2.row();
        label = new Label("ID: 12-3456-789", skin, "body");
        table2.add(label).align(Align.left);

        table2.row();
        label = new Label("Student Statistics", skin, "header2");
        table2.add(label).padTop(40.0f).padBottom(20.0f).align(Align.left);

        table2.row();
        label = new Label("Buildings Explored: 0/20", skin, "body");
        table2.add(label).padBottom(20.0f).align(Align.left);

        table2.row();
        label = new Label("NPCs Interacted: 0/20", skin, "body");
        table2.add(label).align(Align.left);
        table1.add(table2).padLeft(50.0f).align(Align.top);
        stack.addActor(table1);
        table.add(stack);
        stage.addActor(table);
    }

    private void mapTable() {

    }

    private void explorationTable() {

    }

    private void settingsTable() {
        
    }
}
