package io.mygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Abstract class representing an in-game entity with a texture, position, and collision box.
 * Provides functionality for rendering, collision detection, and position updates.
 * This class defines core properties like texture, size, position, and a collision box,
 * with support for complex collision detection via a polygon. Derived classes can extend it
 * to implement specific behavior and rendering.
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

    /**
     * Gets the x-coordinate of the entity's position.
     * @return The x-coordinate of the entity.
     */
    public float getX() { return x; }

    /**
     * Gets the y-coordinate of the entity's position.
     * @return The y-coordinate of the entity.
     */
    public float getY() { return y; }

    /**
     * Gets the width of the entity.
     * @return The width of the entity.
     */
    public float getWidth() { return width; }

    /**
     * Gets the height of the entity.
     * @return The height of the entity.
     */
    public float getHeight() { return height; }

    /**
     * Sets the x-coordinate of the entity's position.
     * Updates the collision box position based on the new x-coordinate.
     * @param newX The new x-coordinate to set.
     */
    public void setX(float newX) {
        this.x = newX;
        updateCollisionPositions();
    }

    /**
     * Sets the y-coordinate of the entity's position.
     * Updates the collision box position based on the new y-coordinate.
     * @param newY The new y-coordinate to set.
     */
    public void setY(float newY) {
        this.y = newY;
        updateCollisionPositions();
    }

    /**
     * Sets the position of the entity.
     * Updates both the x and y coordinates and the collision box position.
     * @param newX The new x-coordinate to set.
     * @param newY The new y-coordinate to set.
     */
    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        updateCollisionPositions();
    }

    /**
     * Sets the width of the entity.
     * @param width The new width to set.
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Sets the height of the entity.
     * @param height The new height to set.
     */
    public void setHeight(float height) {
        this.height = height;
    }
}
