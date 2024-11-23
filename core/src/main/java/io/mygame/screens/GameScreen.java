package io.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import io.mygame.entities.Player;

public class GameScreen extends WildCatScreen {
    private SpriteBatch batch;
    private Player player;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private final float zoomScale = 0.35f;

    // DEBUGGING
    private final boolean debugMode = true;
    private ShapeRenderer shapeRenderer;

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

        // DEBUGGING COLLISION
        shapeRenderer  = new ShapeRenderer();
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

        if (debugMode) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            // Draw the player's collision box
            shapeRenderer.setColor(0, 1, 0, 1); // Green for the player
            Rectangle playerCollisionBox = player.getCollisionBox();
            shapeRenderer.rect(playerCollisionBox.x, playerCollisionBox.y, playerCollisionBox.width, playerCollisionBox.height);

            // Get the "basic collisions" layer as an Object Layer
            MapLayer objectLayer = map.getLayers().get("basic collisions");

            if (objectLayer != null) {
                for (MapObject object : objectLayer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        // Draw RectangleMapObject
                        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                        shapeRenderer.setColor(1, 0, 0, 1); // Red for rectangles
                        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    } else if (object instanceof PolygonMapObject) {
                        // Draw PolygonMapObject
                        Polygon polygon = ((PolygonMapObject) object).getPolygon();
                        shapeRenderer.setColor(0, 0, 1, 1); // Blue for polygons
                        shapeRenderer.polygon(polygon.getTransformedVertices());
                    }
                }
            } else {
                System.out.println("Object layer 'basic collisions' not found!");
            }
            System.out.println("draw buddy");
            shapeRenderer.end();
        }

        // Collision logic (unchanged)
        MapLayer objectLayer = map.getLayers().get("basic collisions");
        if (objectLayer != null) {
            boolean collisionDetected = false;
            for (MapObject object : objectLayer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    if (player.getCollisionBox().overlaps(rectangle)) {
                        collisionDetected = true;
                        break;
                    }
                } else if (object instanceof PolygonMapObject) {
                    Polygon polygon = ((PolygonMapObject) object).getPolygon();
                    if (Intersector.overlapConvexPolygons(player.getCollisionPolygon(), polygon)) {
                        collisionDetected = true;
                        break;
                    }
                }
            }

            if (collisionDetected) {
                System.out.println("Stoppp baa please");
                player.revertToPreviousPosition();
            } else {
                player.savePreviousPosition();
                System.out.println("No Collisions");
            }
        }
    }


    private void draw(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera position to center the player
        camera.position.set(player.getX() + 8, player.getY() + 16, 0); // Offset to center player (16x32 -> 8 and 16)

        // Update the camera's position and zoom level
        camera.zoom = zoomScale;
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        renderer.setView(camera);
        renderer.render();

        // Begin rendering the player
        batch.begin();
        player.update(delta);
        player.render(batch);
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
        shapeRenderer.dispose();
    }
}
