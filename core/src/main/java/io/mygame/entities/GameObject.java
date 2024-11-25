package io.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {
    private final TextureRegion texture;
    private float x, y;
    private float width, height;
    private float collisionWidth, collisionHeight;
    private Rectangle collisionBox;
    private Polygon collisionPolygon;

    public GameObject(TextureRegion texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        initializeCollisionBox();
    }

    public GameObject(float x, float y, float width, float height) {
        this.texture = null;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initializeCollisionBox();
    }

    protected void initializeCollisionBox() {
        collisionWidth = width / 2;
        collisionHeight = height / 4;
        float collisionX = x + (width / 2) - (collisionWidth / 2);
        float yOffset = 5;
        float collisionY = y + (height / 2) - (collisionHeight / 2) - yOffset;
        collisionBox = new Rectangle(collisionX, collisionY, collisionWidth, collisionHeight);
    }

    public void updateBoundingBox(float x, float y) {
        this.x = x;
        this.y = y;

        float yOffset = 5;
        collisionBox.setPosition(
            x + (width / 2) - (collisionWidth / 2),
            y + (height / 2) - (collisionHeight / 2) - yOffset
        );

        if (collisionPolygon != null) {
            collisionPolygon.setPosition(collisionBox.x, collisionBox.y);
        }
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Polygon getCollisionPolygon() {
        if (collisionPolygon == null) {
            collisionPolygon = new Polygon(new float[]{
                0, 0,
                collisionWidth, 0,
                collisionWidth, collisionHeight,
                0, collisionHeight
            });
            collisionPolygon.setOrigin(collisionWidth / 2, collisionHeight / 2);
        }
        collisionPolygon.setPosition(collisionBox.x, collisionBox.y);
        return collisionPolygon;
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }

    public float getWidth() { return width; }
    public float getHeight() { return height; }

    // Setters
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }
}
