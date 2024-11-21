package io.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends GameObject {
    private static final float SPEED = 75f;

    private final Animation<TextureRegion> frontIdleAnimation, rightIdleAnimation, leftIdleAnimation, backIdleAnimation;
    private final Animation<TextureRegion> frontWalkAnimation, rightWalkAnimation, leftWalkAnimation, backWalkAnimation;
    private Animation<TextureRegion> currentAnimation;

    private float stateTime = 0;
    private boolean isMoving = false;
    private Direction direction = Direction.FRONT;

    public Player() {
        super(null, 400, 300);

        Texture spritesheet = new Texture(Gdx.files.internal("Sprites/CollegeMale1.png"));

        TextureRegion[][] tmpFrames = TextureRegion.split(spritesheet, 16, 32);

        frontIdleAnimation = extractFrames(tmpFrames, 0);
        rightIdleAnimation = extractFrames(tmpFrames, 1);
        leftIdleAnimation = extractFrames(tmpFrames, 3);
        backIdleAnimation = extractFrames(tmpFrames, 2);

        frontWalkAnimation = extractFrames(tmpFrames, 4);
        rightWalkAnimation = extractFrames(tmpFrames, 5);
        leftWalkAnimation = extractFrames(tmpFrames, 7);
        backWalkAnimation = extractFrames(tmpFrames, 6);

        currentAnimation = frontIdleAnimation;
    }

    // Helper method to extract frames for animations
    private Animation<TextureRegion> extractFrames(TextureRegion[][] tmpFrames, int column) {
        TextureRegion[] frames = new TextureRegion[6]; // Allocate array for the specified number of frames

        for (int i = 0; i < 6; i++) {
            if (i < tmpFrames.length) {
                frames[i] = tmpFrames[i][column]; // Extract the frame from the specified column
            }
        }

        return new Animation<>(0.1f, frames); // Create an animation with the extracted frames
    }

    public void update(float delta) {
        stateTime += delta;  // Update the state time for animation

        if (isMoving) {

            switch (direction) {
                case FRONT, FRONT_RIGHT, FRONT_LEFT:
                    currentAnimation = frontWalkAnimation;
                    break;
                case RIGHT:
                    currentAnimation = rightWalkAnimation;
                    break;
                case LEFT:
                    currentAnimation = leftWalkAnimation;
                    break;
                case BACK, BACK_RIGHT, BACK_LEFT:
                    currentAnimation = backWalkAnimation;
                    break;
            }
        } else {

            switch (direction) {
                case FRONT:
                    currentAnimation = frontIdleAnimation;
                    break;
                case RIGHT, FRONT_RIGHT, BACK_RIGHT:
                    currentAnimation = rightIdleAnimation;
                    break;
                case LEFT, FRONT_LEFT, BACK_LEFT:
                    currentAnimation = leftIdleAnimation;
                    break;
                case BACK:
                    currentAnimation = backIdleAnimation;
                    break;
            }
        }
    }

    // Render the current animation frame
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y, 16, 32); // Draw at the current position
    }

    public void moveUp() {
        y += SPEED * Gdx.graphics.getDeltaTime();
        direction = Direction.BACK;
        isMoving = true;
    }

    public void moveDown() {
        y -= SPEED * Gdx.graphics.getDeltaTime();
        direction = Direction.FRONT;
        isMoving = true;
    }

    public void moveLeft() {
        x -= SPEED * Gdx.graphics.getDeltaTime();
        direction = Direction.LEFT;
        isMoving = true;
    }

    public void moveRight() {
        x += SPEED * Gdx.graphics.getDeltaTime();
        direction = Direction.RIGHT;
        isMoving = true;
    }

    public void moveUpRight() {
        float diagonalSpeed = SPEED * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        x += diagonalSpeed;
        y += diagonalSpeed;
        direction = Direction.RIGHT;
        isMoving = true;
    }

    public void moveUpLeft() {
        float diagonalSpeed = SPEED * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        x -= diagonalSpeed;
        y += diagonalSpeed;
        direction = Direction.LEFT;
        isMoving = true;
    }

    public void moveDownRight() {
        float diagonalSpeed = SPEED * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        x += diagonalSpeed;
        y -= diagonalSpeed;
        direction = Direction.RIGHT;
        isMoving = true;
    }

    public void moveDownLeft() {
        float diagonalSpeed = SPEED * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        x -= diagonalSpeed;
        y -= diagonalSpeed;
        direction = Direction.LEFT;
        isMoving = true;
    }


    public void stop() {
        isMoving = false;
    }

    public enum Direction {
        FRONT, RIGHT, LEFT, BACK, FRONT_RIGHT, FRONT_LEFT, BACK_RIGHT, BACK_LEFT
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
