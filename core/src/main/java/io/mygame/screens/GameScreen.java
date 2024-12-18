package io.mygame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.mygame.common.CollisionHandler;
import io.mygame.common.GameManager;
import io.mygame.common.MapHandler;
import io.mygame.entities.Entity;
import io.mygame.entities.NPC;
import io.mygame.factories.NPCFactory;
import io.mygame.entities.Player;
import io.mygame.ui.DialogueUI;
import io.mygame.ui.MainGameUI;
import io.mygame.ui.UI;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game screen where gameplay occurs.
 * Manages rendering, player and NPC interactions, collision handling, and UI updates.
 */
public class GameScreen extends WildCatScreen {
    /************ RENDERERS ************/
    private SpriteBatch batch;

    /************ ENTITY ************/
    private Player player;
    private List<NPC> npcs;

    /************ COLLISION HANDLER ************/
    private CollisionHandler collisionHandler;

    /************ USER INTERFACES ***********/
    private UI mainGameUI;
    private UI dialogueUI;

    /************ SCREEN VIEWPORT SIZE ************/
    private Viewport viewport;
    private OrthographicCamera camera;

    /************ MAP HANDLING ************/
    private MapHandler mapHandler;
    private TiledMap map;

    /************ GAME STATE ************/
    private boolean isPaused = false;
    private boolean drawOverlay = false;

    /************ SOUND HANDLING ************/
    private float footstepTimer = 0f;
    private static final float FOOTSTEP_DELAY = 0.5f;

    /************ GAME MANAGEMENT ************/
    private static final GameManager gameManagerInstance = GameManager.getInstance();

    /************ RENDERING ************/
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    /************ SOUND HANDLING ************/
    private boolean isExplorationDone = false;

    /**
     * Constructor for the GameScreen class.
     *
     * @param game the main game instance
     */
    public GameScreen(Game game) {
        super(game);
    }

    /**
     * Called when the screen is shown. Initializes the map, renderer, camera, viewport, player, and other components.
     */
    @Override
    public void show() {
        ScreenViewport sharedViewport = new ScreenViewport();

        map = new TmxMapLoader().load("PixelMaps/TestMap.tmx");
        mapHandler = new MapHandler(map);
        camera = new OrthographicCamera();
        int WORLD_HEIGHT = 360;
        int SCREEN_WIDTH = 640;
        viewport = new ExtendViewport(SCREEN_WIDTH, WORLD_HEIGHT, camera);

        batch = new SpriteBatch();
        player = new Player();

        dialogueUI = new DialogueUI(sharedViewport, this, game);
        mainGameUI = new MainGameUI(sharedViewport,this, game);

        npcs = new ArrayList<>();

        npcs = NPCFactory.createNPCs();
        collisionHandler = new CollisionHandler(player, npcs, map, camera);
    }

    /**
     * Renders the screen. Called every frame.
     *
     * @param delta the time in seconds since the last frame
     */
    @Override
    public void render(float delta) {
        draw();

        if(!isPaused) {
            input(delta);
            entityLogic();
        }

        mapSignLogic();
        dialogueLogic();

        mainGameUI.render();
        dialogueUI.render();
    }

    /**
     * Handles the drawing of the map, player, and other graphical elements.
     */
    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + 8, player.getY() + 16, 0);
        camera.update();
        viewport.apply();

        batch.setProjectionMatrix(camera.combined);
        mapHandler.loadBackground(camera);

        batch.begin();
        player.update();

        List<Entity> renderQueue = new ArrayList<>();
        renderQueue.add(player);
        renderQueue.addAll(npcs);

        renderQueue.sort((a, b) -> Float.compare(b.getY(), a.getY()));

        for (Entity obj : renderQueue) {
            obj.render(batch);
        }
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (drawOverlay) shapeRenderer.setColor(0, 0, 0, 0.5f);
        else shapeRenderer.setColor(0, 0, 0, 0.0f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        mapHandler.loadForeground();
    }

    /**
     * Processes player input for movement.
     */
    private void input(float delta) {
        player.stop();

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
            gameManagerInstance.getSoundManager().addSound("walk");
            footstepTimer = 0f;
        }

        ((MainGameUI) mainGameUI).keyMenuHandler();
        gameManager.setPlayerPosition((int)player.getX(), (int)player.getY());
    }


    /**
     * Contains game logic updates, including collision handling and player bounding box updates.
     */
    private void entityLogic() {
        player.updateBoundingBox(player.getX(), player.getY());
        collisionHandler.handlePlayerCollision();

        for(NPC npc : npcs){
            npc.update();
        }

        collisionHandler.handleNpcCollision();

        if(gameManagerInstance.getNpcFound() == 6 && gameManagerInstance.getBuildingsFound() == 12 &&
            !isExplorationDone) {
            System.out.println("ey");
            ((MainGameUI) mainGameUI).congratsUI();
            isExplorationDone = true;
        }
    }

    /**
     * Handles logic related to map signs interactions.
     */
    private void mapSignLogic() {
        String type = collisionHandler.checkObjectInteractions();
        if(type != null) {
            ((MainGameUI) mainGameUI).signDescription(type);
        }
    }

    /**
     * Handles logic related to NPC interactions.
     */
    private void dialogueLogic() {
        String npcType = collisionHandler.checkNPCInteractions();
        if (npcType != null) {
            ((DialogueUI) dialogueUI).dialogueBox(npcType);
        }
    }

    /**
     * Pauses or unpauses the game.
     * @param state true to pause, false to unpause
     */
    public void setPaused(boolean state) {
        this.isPaused = state;
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
        collisionHandler.dispose();
        mapHandler.dispose();
    }

    /**
     * Sets the overlay visibility.
     * @param visible true to show the overlay, false to hide it
     */
    public void setOverlayVisible(boolean visible) {
        this.drawOverlay = visible;
    }

    /**
     * Changes the current screen to a new GameScreen.
     *
     * @param screen the new screen to be displayed
     */
    @Override
    public void changeScreen(Screen screen) {
        super.changeScreen(screen);
    }
}
