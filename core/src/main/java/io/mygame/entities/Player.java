package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.mygame.common.AnimationLoader;
import io.mygame.common.GameManager;
import io.mygame.common.InputHandler;
import io.mygame.enums.Direction;

/**
 * Represents a player entity in the game. This class is responsible for handling player movement,
 * animation, and rendering. It extends from the {@link Entity} class and includes functionality
 * for updating the player’s state, handling input, and rendering the player’s animations.
 * The player can move in multiple directions, and animations are updated based on the player’s
 * movement state.
 */
public class Player extends Entity {

    /************ PLAYER MOVEMENT ************/
    private static final float SPEED = 100f;

    /************ PLAYER ANIMATION ************/
    private final AnimationLoader playerAnimation;

    /************ STATE AND DIRECTION ************/
    private float stateTime = 0; // Time tracker for animation
    private boolean isMoving = false; // Whether the player is moving
    private Direction direction = Direction.FRONT; // Direction of movement

    /************ PLAYER INPUT HANDLER ************/
    private final InputHandler inputHandler = new InputHandler(this);

    /**
     * Constructs a new Player instance with the default size and sets the initial position.
     * Initializes the player’s animation and input handler.
     */
    public Player() {
        super(16, 32); // Calls parent constructor with specific size
        GameManager gameManager = GameManager.getInstance();
        setPosition(gameManager.getXPosition(), gameManager.getYPosition());
        playerAnimation = new AnimationLoader("Sprites/Player.png"); // Loads player animation
    }

    /**
     * Updates the player’s animation state based on movement and direction.
     * This method should be called every frame to ensure animations are kept in sync with the player's movement.
     */
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();

        stateTime += delta;  // Update the state time for animation

        if (isMoving) {
            // Update the animation depending on the direction of movement
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
            // Set idle animation when not moving
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

    /**
     * Renders the player’s current animation frame to the screen at the player’s current position.
     *
     * @param batch the {@link SpriteBatch} used to render the player’s animation
     */
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = playerAnimation.getCurrentAnimation().getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight()); // Draw at the current position
    }

    /**
     * Moves the player upwards.
     */
    public void moveUp() {
        inputHandler.moveUp();
    }

    /**
     * Moves the player downwards.
     */
    public void moveDown() {
        inputHandler.moveDown();
    }

    /**
     * Moves the player to the left.
     */
    public void moveLeft() {
        inputHandler.moveLeft();
    }

    /**
     * Moves the player to the right.
     */
    public void moveRight() {
        inputHandler.moveRight();
    }

    /**
     * Moves the player diagonally upwards to the right.
     */
    public void moveUpRight() {
        inputHandler.moveUpRight();
    }

    /**
     * Moves the player diagonally upwards to the left.
     */
    public void moveUpLeft() {
        inputHandler.moveUpLeft();
    }

    /**
     * Moves the player diagonally downwards to the right.
     */
    public void moveDownRight() {
        inputHandler.moveDownRight();
    }

    /**
     * Moves the player diagonally downwards to the left.
     */
    public void moveDownLeft() {
        inputHandler.moveDownLeft();
    }

    /**
     * Stops the player's movement and sets the movement state to false.
     */
    public void stop() {
        isMoving = false;
    }

    /**
     * Retrieves the player's speed.
     *
     * @return the player's speed
     */
    public float getSPEED() {
        return SPEED;
    }

    /**
     * Sets the player's movement state.
     *
     * @param isMoving the new movement state
     */
    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * Sets the player's current direction.
     *
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
