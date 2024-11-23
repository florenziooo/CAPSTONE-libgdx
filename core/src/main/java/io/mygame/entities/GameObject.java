package io.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {
    protected TextureRegion texture;
    protected float x, y;
    protected float width, height;
    public Rectangle collisionBox;

    public GameObject(TextureRegion texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.collisionBox = new Rectangle(x, y, width, height);
    }

    public GameObject(float x, float y, float width, float height) {
        texture = null;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collisionBox = new Rectangle(x, y, width, height);
    }


    public void updateBoundingBox(int x, int y) {
        collisionBox.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }


}
