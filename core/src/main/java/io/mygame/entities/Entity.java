package io.mygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    private final TextureRegion textureRegion;
    private float x, y;
    private float width, height;
    protected float collisionWidth, collisionHeight;
    protected Rectangle collisionBox;
    private Polygon collisionPolygon;
    private final float yOffset = 5; // Made constant to ensure consistency

    public Entity(TextureRegion textureRegion, float x, float y) {
        this.textureRegion = textureRegion;
        this.x = x;
        this.y = y;
        this.width = textureRegion.getRegionWidth();
        this.height = textureRegion.getRegionHeight();
        initializeCollisionBox();
    }

    public Entity(Texture npcTexture, float x, float y) {
        this.textureRegion = new TextureRegion(npcTexture, 0, 0, 16, 32);;
        this.x = x;
        this.y = y;
        this.width = textureRegion.getRegionWidth();
        this.height = textureRegion.getRegionHeight();
        initializeCollisionBox();
    }

    public Entity(float x, float y, float width, float height) {
        this.textureRegion = null;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initializeCollisionBox();
    }

    protected void initializeCollisionBox() {
        collisionWidth = width / 2;
        collisionHeight = height / 4;
        float collisionX = calculateCollisionX();
        float collisionY = calculateCollisionY();
        collisionBox = new Rectangle(collisionX, collisionY, collisionWidth, collisionHeight);
    }

    private float calculateCollisionX() {
        return x + (width / 2) - (collisionWidth / 2);
    }

    private float calculateCollisionY() {
        return y + (height / 2) - (collisionHeight / 2) - yOffset;
    }

    public void updateBoundingBox(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        updateCollisionPositions();
    }

    private void updateCollisionPositions() {
        collisionBox.setPosition(calculateCollisionX(), calculateCollisionY());

        if (collisionPolygon != null) {
            collisionPolygon.setPosition(collisionBox.x, collisionBox.y);
        }
    }

    public void render(SpriteBatch batch) {
        if (textureRegion != null) {
            batch.draw(textureRegion, x, y);
        }
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Polygon getCollisionPolygon() {
        if (collisionPolygon == null) {
            // Create polygon vertices relative to origin (0,0)
            collisionPolygon = new Polygon(new float[]{
                0, 0,
                collisionWidth, 0,
                collisionWidth, collisionHeight,
                0, collisionHeight
            });
            collisionPolygon.setOrigin(collisionWidth / 2, collisionHeight / 2);
            collisionPolygon.setPosition(collisionBox.x, collisionBox.y);
        }
        return collisionPolygon;
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }

    public float getWidth() { return width; }
    public float getHeight() { return height; }

    // Setters
    public void setX(float newX) {
        this.x = newX;
        updateCollisionPositions();
    }

    public void setY(float newY) {
        this.y = newY;
        updateCollisionPositions();
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        updateCollisionPositions();
    }

    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }
}
