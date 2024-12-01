package io.mygame.screens;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.mygame.common.CollisionHandler;
import io.mygame.common.SoundManager;
import io.mygame.entities.Player;
import io.mygame.ui.MainGameUI;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends WildCatScreen {
    /************ RENDERERS ************/
    private SpriteBatch batch;
    private OrthogonalTiledMapRenderer renderer;

    /************ ENTITY ************/
    private Player player;

    /************ COLLISION HANDLER ************/
    private CollisionHandler collisionHandler;

    /************ USER INTERFACES ***********/
    private MainGameUI mainGameUI;

    /************ SCREEN VIEWPORT SIZE ************/
    private Viewport viewport;
    private OrthographicCamera camera;
    private final int SCREEN_WIDTH = 640;
    private final int WORLD_HEIGHT = 360;

    /************ TILED MAPS ************/
    private TiledMap map;
    private List<TiledMapTileLayer> background;
    private List<TiledMapTileLayer> foreground;

    private float footstepTimer = 0f; // timer for footstep sound
    private static final float FOOTSTEP_DELAY = 0.5f; // delay in seconds between footsteps

    public GameScreen(Game game, SoundManager sound) {
    /**
     * Constructor for the GameScreen class.
     *
     * @param game the main game instance
     */
    public GameScreen(Game game) {
        super(game);
        this.sound = sound; // reuse the SoundManager from the MainMenu
    }

    /**
     * Called when the screen is shown. Initializes the map, renderer, camera, viewport, player, and other components.
     */
    @Override
    public void show() {
        map = new TmxMapLoader().load("PixelMaps/TestMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, WORLD_HEIGHT, camera);

        batch = new SpriteBatch();
        player = new Player();

        mainGameUI = new MainGameUI();

        MapLayers mapLayers = map.getLayers();
        background = new ArrayList<>();
        foreground = new ArrayList<>();

        // Iterate through each layer
        for (MapLayer layer : mapLayers) {
            try {
                String layerName = layer.getName();
                System.out.println("Layer Name: " + (layerName != null ? layerName : "Unnamed Layer"));

                if (layer instanceof TiledMapTileLayer tileLayer) {
                    String type = null;
                    try {
                        type = tileLayer.getProperties().get("type", String.class);
                    } catch (ClassCastException e) {
                        System.err.println("Error: 'type' property in layer '" + layerName + "' is not a String.");
                        throw new RuntimeException("Error: " + e.getMessage());
                    }

                    if (type != null) {
                        if (type.equals("foreground")) {
                            foreground.add(tileLayer);
                        } else {
                            background.add(tileLayer);
                            System.out.println("Background type: " + type);
                        }
                    } else {
                        background.add(tileLayer);
                        System.out.println("Layer '" + layerName + "' does not have a 'type' property.");
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred while processing a layer: " + e.getMessage());
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }

        collisionHandler = new CollisionHandler(player, map, camera);
    }

    /**
     * Renders the screen. Called every frame.
     *
     * @param delta the time in seconds since the last frame
     */
    @Override
    public void render(float delta) {
        input(delta);
        draw();
        logic();
        mainGameUI.render();
    }

    /**
     * Handles the drawing of the map, player, and other graphical elements.
     */
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

    /**
     * Processes player input for movement.
     */
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


    /**
     * Contains game logic updates, including collision handling and player bounding box updates.
     */
    private void logic() {
        player.updateBoundingBox(player.getX(), player.getY());
        collisionHandler.handleCollision();
        System.out.println(player.getX() + " " + player.getY());
    }

    /**
     * Resizes the viewport and HUD when the screen dimensions change.
     *
     * @param width  the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        mainGameUI.resize(width, height);
    }

    /**
     * Hides the screen and disposes of resources.
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * Disposes of all resources used by the screen.
     */
    @Override
    public void dispose() {
        mainGameUI.dispose();
        batch.dispose();
        map.dispose();
        renderer.dispose();
        collisionHandler.dispose();
    }

    /**
     * Changes the current screen to a new GameScreen.
     *
     * @param screen the new screen to be displayed
     */
    @Override
    public void changeScreen(GameScreen screen) {
        game.setScreen(screen);
    }
}
