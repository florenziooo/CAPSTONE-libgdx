package io.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.entity.Player;

public class GameScreen implements Screen {
    private static final int TILE_SIZE = 48; // 16 * 3
    private static final int SCREEN_WIDTH = TILE_SIZE * 16;
    private static final int SCREEN_HEIGHT = TILE_SIZE * 12;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        batch = new SpriteBatch();

        // Load map
        map = new TmxMapLoader().load("assets/PixelMaps/TestMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);


        player = new Player();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera position to center the player
        camera.position.set(player  .getX() + 8, player.getY() + 16, 0); // Offset to center player (16x32 -> 8 and 16)

        // Update the camera's position and zoom level
        camera.zoom = 0.25f;  // Adjust this value as needed
        camera.update();

        // Apply the camera transformation
        batch.setProjectionMatrix(camera.combined);

        // Set the map renderer view to the camera's position
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Begin rendering the player
        batch.begin();
        player.update(delta);
        player.render(batch);
        batch.end();

        // Handle input to move the player
        handleInput();
    }

    private void handleInput() {

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



    @Override
    public void resize(int width, int height) {
        // Adjust camera viewport size based on screen resolution and zoom level
        camera.viewportWidth = width / camera.zoom;
        camera.viewportHeight = height / camera.zoom;
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
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
