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

import io.mygame.common.CollisionHandler;
import io.mygame.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends WildCatScreen {
    private SpriteBatch batch;
    private Player player;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private CollisionHandler collisionHandler;

    private List<TiledMapTileLayer> background;
    private List<TiledMapTileLayer> foreground;

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

        MapLayers mapLayers = map.getLayers();
        background = new ArrayList<>();
        foreground = new ArrayList<>();

        // Iterate through each layer
        for (MapLayer layer : mapLayers) {
            System.out.println("Layer Name: " + layer.getName());

            // Check if it's a tile layer
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;

                // Safely retrieve the "type" property
                String type = tileLayer.getProperties().get("type", String.class); // Cast property to String
                System.out.println("Type: " + type);

                if (type != null) {
                    if (type.equals("foreground")) {
                        foreground.add(tileLayer);;
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
        input();
        logic();
        draw();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.position.set(player.getX() + 8, player.getY() + 16, 0);
        camera.zoom = 0.35f;
        camera.update();

        // Set projection matrices
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
    }

    private void logic() {
        player.updateBoundingBox(player.getX(), player.getY());
        collisionHandler.handleCollision();
        System.out.println(player.getX() + " " + player.getY());
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height / camera.zoom;
        camera.viewportWidth = width / camera.zoom;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
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
