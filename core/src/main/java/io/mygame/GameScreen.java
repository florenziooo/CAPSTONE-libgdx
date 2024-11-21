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

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private static final int TILE_SIZE = 48; // 16 * 3
    private static final int SCREEN_WIDTH = TILE_SIZE * 16;
    private static final int SCREEN_HEIGHT = TILE_SIZE * 12;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private AssetManager assetManager;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        batch = new SpriteBatch();

        // Load map
        map = new TmxMapLoader().load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // Initialize player and assets
        player = new Player(this);
        assetManager = new AssetManager(this);
        assetManager.setupObjects();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        player.update(delta);
        player.render(batch);
        assetManager.renderObjects(batch);
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) player.moveUp();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) player.moveDown();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) player.moveLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) player.moveRight();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
