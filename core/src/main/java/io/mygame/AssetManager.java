package io.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

public class AssetManager {
    private GameScreen gameScreen;
    private TextureAtlas textureAtlas;
    private List<GameObject> objects;

    public AssetManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.objects = new ArrayList<>();

        // Load texture atlas
        textureAtlas = new TextureAtlas(Gdx.files.internal("game_objects.atlas"));
    }

    public void setupObjects() {
        // Seagulls
        addSeagull(23 * 48, 7 * 48);
        addSeagull(23 * 48, 40 * 48);
        addSeagull(37 * 48, 7 * 48);
        addSeagull(8 * 48, 28 * 48);
        addSeagull(12 * 48, 22 * 48);

        // Doors
        addDoor(10 * 48, 11 * 48);
        addDoor(16 * 48, 21 * 48);

        // Chest
        addChest(10 * 48, 7 * 48);

        // Boots
        addBoots(37 * 48, 42 * 48);
    }

    private void addSeagull(float x, float y) {
        TextureRegion seagullTexture = textureAtlas.findRegion("seagull");
        objects.add(new GameObject(seagullTexture, x, y));
    }

    private void addDoor(float x, float y) {
        TextureRegion doorTexture = textureAtlas.findRegion("door");
        objects.add(new GameObject(doorTexture, x, y));
    }

    private void addChest(float x, float y) {
        TextureRegion chestTexture = textureAtlas.findRegion("chest");
        objects.add(new GameObject(chestTexture, x, y));
    }

    private void addBoots(float x, float y) {
        TextureRegion bootsTexture = textureAtlas.findRegion("boots");
        objects.add(new GameObject(bootsTexture, x, y));
    }

    public void renderObjects(SpriteBatch batch) {
        for (GameObject obj : objects) {
            obj.render(batch);
        }
    }
}
