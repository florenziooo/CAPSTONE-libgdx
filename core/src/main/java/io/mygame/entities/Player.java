package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import io.mygame.common.AnimationLoader;
import io.mygame.common.InputHandler;

public class Player extends GameObject {
    private static final float SPEED = 75f;
    private final AnimationLoader playerAnimation;

    private float stateTime = 0;
    private boolean isMoving = false;
    private Direction direction = Direction.FRONT;
    private InputHandler inputHandler = new InputHandler(this);

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
        inputHandler.moveUp();
    }

    public void moveDown() {
        inputHandler.moveDown();
    }

    public void moveLeft() {
        inputHandler.moveLeft();
    }

    public void moveRight() {
        inputHandler.moveRight();
    }

    public void moveUpRight() {
        inputHandler.moveUpRight();
    }

    public void moveUpLeft() {
        inputHandler.moveUpLeft();
    }

    public void moveDownRight() {
        inputHandler.moveDownRight();
    }

    public void moveDownLeft() {
        inputHandler.moveDownLeft();
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

    public float getSPEED() {
        return SPEED;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
