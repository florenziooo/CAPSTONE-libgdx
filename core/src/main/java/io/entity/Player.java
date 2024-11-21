package io.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.mygame.GameScreen;

public class Player extends GameObject {
    private static final float SPEED = 200f;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime = 0;

    public Player(GameScreen gameScreen) {
        super(null, 400, 300);  // Starting position

        // Load player animations
        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("player.atlas"));
        TextureRegion[] walkFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            walkFrames[i] = playerAtlas.findRegion("walk-" + i);
        }
        walkAnimation = new Animation<>(0.25f, walkFrames);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);
    }

    public void moveUp() {
        y += SPEED * Gdx.graphics.getDeltaTime();
    }

    public void moveDown() {
        y -= SPEED * Gdx.graphics.getDeltaTime();
    }

    public void moveLeft() {
        x -= SPEED * Gdx.graphics.getDeltaTime();
    }

    public void moveRight() {
        x += SPEED * Gdx.graphics.getDeltaTime();
    }
}
