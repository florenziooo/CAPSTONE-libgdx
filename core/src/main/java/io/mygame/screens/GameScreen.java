package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.mygame.common.CollisionHandler;
import io.mygame.common.SoundManager;
import io.mygame.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends WildCatScreen {
    private SpriteBatch batch;
    private Player player;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private CollisionHandler collisionHandler;

    private final int SCREEN_WIDTH = 640;
    private final int WORLD_HEIGHT = 360;

    private List<TiledMapTileLayer> background;
    private List<TiledMapTileLayer> foreground;

    private float footstepTimer = 0f; // timer for footstep sound
    private static final float FOOTSTEP_DELAY = 0.5f; // delay in seconds between footsteps

    public GameScreen(Game game, SoundManager sound) {
        super(game);
        this.sound = sound; // reuse the SoundManager from the MainMenu
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("PixelMaps/TestMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, WORLD_HEIGHT, camera);

        batch = new SpriteBatch();
        player = new Player();

        MapLayers mapLayers = map.getLayers();
        background = new ArrayList<>();
        foreground = new ArrayList<>();

        // Iterate through each layer
        for (MapLayer layer : mapLayers) {
            System.out.println("Layer Name: " + layer.getName());

            // Check if it's a tile layer
            if (layer instanceof TiledMapTileLayer tileLayer) {

                // Safely retrieve the "type" property
                String type = tileLayer.getProperties().get("type", String.class); // Cast property to String
                System.out.println("Type: " + type);

                if (type != null) {
                    if (type.equals("foreground")) {
                        foreground.add(tileLayer);
                    } else {
                        background.add(tileLayer);
                        System.out.println(tileLayer.getProperties().get("type", String.class));
                    }
                } else {
                    background.add(tileLayer);
                    System.out.println("Layer '" + tileLayer.getName() + "' does not have a 'type' property.");
                }
            }
        }

        collisionHandler = new CollisionHandler(player, map, camera);
    }


    @Override
    public void render(float delta) {
        input(delta);
        draw();
        logic();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.position.set(player.getX() + 8, player.getY() + 16, 0);
        camera.update();
        viewport.apply();

        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);

        renderer.getBatch().begin();
        System.out.println("Background:");
        for(TiledMapTileLayer layer : background) {
            renderer.renderTileLayer(layer);
            System.out.println(layer.getName());
        }
        renderer.getBatch().end();

        batch.begin();
        System.out.println("Drawing Player");
        player.update();
        player.render(batch);
        batch.end();

        renderer.getBatch().begin();
        System.out.println("Foreground:");
        for(TiledMapTileLayer layer : foreground) {
            System.out.println(layer.getName());
            renderer.renderTileLayer(layer);
        }
        renderer.getBatch().end();
    }

    private void input(float delta) {
        player.stop();

        // Update the footstep timer
        footstepTimer += delta;

        boolean isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveUpRight();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveUpLeft();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveDownRight();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveDownLeft();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
            isMoving = true;
        }

        if (isMoving && footstepTimer >= FOOTSTEP_DELAY) {
            sound.addSound("walk");
            footstepTimer = 0f;
        }
    }


    private void logic() {
        player.updateBoundingBox(player.getX(), player.getY());
        collisionHandler.handleCollision();
        System.out.println(player.getX() + " " + player.getY());
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
        viewport.update(width, height);
    }

    @Override
    public void hide() {
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
