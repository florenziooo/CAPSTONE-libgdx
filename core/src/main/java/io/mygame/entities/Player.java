package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import io.mygame.common.AnimationLoader;

public class Player extends GameObject {
    private static final float SPEED = 75f;
    private final AnimationLoader playerAnimation;

    private float stateTime = 0;
    private boolean isMoving = false;
    private Direction direction = Direction.FRONT;

    public Player() {
        super(144, 0, 16, 32);
        playerAnimation = new AnimationLoader("Sprites/CollegeMale1.png");
    }

    public void update(float delta) {
        stateTime += delta;  // Update the state time for animation

        if (isMoving) {

            switch (direction) {
                case FRONT, FRONT_RIGHT, FRONT_LEFT:
                    playerAnimation.setCurrentAnimation("frontWalk");
                    break;
                case RIGHT:
                    playerAnimation.setCurrentAnimation("rightWalk");
                    break;
                case LEFT:
                    playerAnimation.setCurrentAnimation("leftWalk");
                    break;
                case BACK, BACK_RIGHT, BACK_LEFT:
                    playerAnimation.setCurrentAnimation("backWalk");
                    break;
            }
        } else {

            switch (direction) {
                case FRONT:
                    playerAnimation.setCurrentAnimation("frontIdle");
                    break;
                case RIGHT, FRONT_RIGHT, BACK_RIGHT:
                    playerAnimation.setCurrentAnimation("rightIdle");
                    break;
                case LEFT, FRONT_LEFT, BACK_LEFT:
                    playerAnimation.setCurrentAnimation("leftIdle");
                    break;
                case BACK:
                    playerAnimation.setCurrentAnimation("backIdle");
                    break;
            }
        }
    }

    // Render the current animation frame
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = playerAnimation.getCurrentAnimation().getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y, width, height); // Draw at the current position
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

    public Polygon getCollisionPolygon() {
        Polygon polygon = new Polygon(new float[]{
            getX(), getY(),
            getX() + getWidth(), getY(),
            getX() + getWidth(), getY() + getHeight(),
            getX(), getY() + getHeight()
        });
        return polygon;
    }

    private float previousX, previousY;

    public void savePreviousPosition() {
        previousX = getX();
        previousY = getY();
    }

    public void revertToPreviousPosition() {
        x = previousX;
        y = previousY;
    }


    public void stop() {
        isMoving = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
