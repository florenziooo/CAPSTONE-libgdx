package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.mygame.common.CollisionHandler;
import io.mygame.entities.Player;
import io.mygame.entities.NPC;
import io.mygame.factories.EntityFactory;

public class GameScreen extends WildCatScreen {
    private SpriteBatch batch;
    private Player player;
    private NPC npc;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private CollisionHandler collisionHandler;

    public GameScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        map = new TmxMapLoader().load("PixelMaps/TestMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        batch = new SpriteBatch();
        player = new Player();

        npc = EntityFactory.createNPC("Sprites/CITBOY.png", 144, 64);

        collisionHandler = new CollisionHandler(player, npc, map, camera);
    }


    @Override
    public void render(float delta) {
        input();
        draw(delta);
        logic();
    }

    private void input() {
        player.stop();

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveUpRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveUpLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveDownRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveDownLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }

        // Stop movement if no key is pressed
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) &&
            !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.stop();
        }
    }

    private void logic() {
        player.updateBoundingBox(player.getX(), player.getY());
        collisionHandler.handleCollision();
    }


    private void draw(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera position to center the player
        camera.position.set(player.getX() + 8, player.getY() + 16, 0); // Offset to center player (16x32 -> 8 and 16)

        // Update the camera's position and zoom level
        camera.zoom = 0.35f;
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        renderer.setView(camera);
        renderer.render();

        // Begin rendering the player
        batch.begin();
        player.update(delta);

        // Determine rendering order based on y-coordinate
        if (player.getY() > npc.getY()) {
            player.render(batch); // Player is behind NPC
            npc.render(batch);
        } else {
            npc.render(batch); // NPC is behind Player
            player.render(batch);
        }

        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        // Adjust camera viewport size based on screen resolution and zoom level
        camera.viewportHeight = height / camera.zoom;
        camera.viewportWidth = width / camera.zoom;
        camera.update();
    }


    @Override
    public void pause() {
        // Invoked when your application is paused.
    }


    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }


    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
        dispose();
    }


    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        renderer.dispose();
        collisionHandler.dispose();
    }
}
