package io.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameObject {
    protected TextureRegion texture;
    protected float x, y;

    public GameObject(TextureRegion texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
