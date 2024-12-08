package io.mygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Abstract class representing an in-game entity with a texture, position, and collision box.
 * Provides functionality for rendering the entity, handling collision detection, and updating its position.
 * <p>
 * This class defines the core properties of an entity, such as its texture, size, and position.
 * It also includes a collision box (represented as a rectangle) and optionally a collision polygon for more complex collision detection.
 * Derived classes can extend this class to implement specific behavior and rendering logic.
 */
public abstract class Entity {
    /************ ENTITY TEXTURE ************/
    private final TextureRegion textureRegion;

    /************ ENTITY POSITION ************/
    private float x, y;

    /************ ENTITY DIMENSIONS ************/
    private float width, height;

    /************ COLLISION PARAMETERS ************/
    protected float collisionWidth, collisionHeight;
    protected Rectangle collisionBox;
    private Polygon collisionPolygon;

    /**
     * Constructs an Entity with a texture and initial position.
     *
     * @param npcTexture the texture for the entity
     * @param x the initial x position
     * @param y the initial y position
     */
    public Entity(Texture npcTexture, float x, float y) {
        this.textureRegion = new TextureRegion(npcTexture, 0, 0, 16, 32);
        this.x = x;
        this.y = y;
        this.width = textureRegion.getRegionWidth();
        this.height = textureRegion.getRegionHeight();
        initializeCollisionBox();
    }

    /**
     * Constructs an Entity with a specified size (without a texture).
     *
     * @param width the width of the entity
     * @param height the height of the entity
     */
    public Entity(float width, float height) {
        this.textureRegion = null;
        this.width = width;
        this.height = height;
        initializeCollisionBox();
    }

    /**
     * Initializes the collision box for the entity based on its dimensions.
     */
    protected void initializeCollisionBox() {
        collisionWidth = width / 2;
        collisionHeight = height / 4;
        float collisionX = calculateCollisionX();
        float collisionY = calculateCollisionY();
        collisionBox = new Rectangle(collisionX, collisionY, collisionWidth, collisionHeight);
    }

    /**
     * Calculates the x position of the collision box.
     *
     * @return the calculated x position
     */
    private float calculateCollisionX() {
        return x + (width / 2) - (collisionWidth / 2);
    }

    /**
     * Calculates the y position of the collision box.
     *
     * @return the calculated y position
     */
    private float calculateCollisionY() {
        float yOffset = 5;
        return y + (height / 2) - (collisionHeight / 2) - yOffset;
    }

    /**
     * Updates the entity's position and its collision box accordingly.
     *
     * @param newX the new x position
     * @param newY the new y position
     */
    public void updateBoundingBox(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        updateCollisionPositions();
    }

    /**
     * Updates the position of the collision box and the collision polygon (if present).
     */
    private void updateCollisionPositions() {
        collisionBox.setPosition(calculateCollisionX(), calculateCollisionY());

        if (collisionPolygon != null) {
            collisionPolygon.setPosition(collisionBox.x, collisionBox.y);
        }
    }

    /**
     * Renders the entity's texture to the screen.
     *
     * @param batch the SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        if (textureRegion != null) {
            batch.draw(textureRegion, x, y);
        }
    }

    /**
     * Retrieves the collision box of the entity.
     *
     * @return the collision box
     */
    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    /**
     * Retrieves the collision polygon for more complex collision detection.
     *
     * @return the collision polygon
     */
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
