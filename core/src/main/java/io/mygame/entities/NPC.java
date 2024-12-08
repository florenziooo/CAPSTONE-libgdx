package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.mygame.common.AnimationLoader;
import io.mygame.enums.Direction;

/**
 * Represents an NPC (Non-Player Character) entity in the game. This class is responsible for handling
 * NPC movement, animation, and rendering. It extends from the {@link Entity} class and includes
 * functionality for updating the NPC’s state and rendering the NPC’s animations.
 * The NPC can move in different patterns (targeted, horizontal, vertical) and animations are updated
 * based on the NPC’s movement state.
 */
public abstract class NPC extends Entity {

    /************ NPC TYPE AND IDENTIFICATION ************/
    private final String npcType;

    /************ NPC SPEED ************/
    private static final float SPEED = 75f;

    /************ NPC ANIMATION ************/
    private final AnimationLoader npcAnimation;

    /************ STATE AND DIRECTION ************/
    private float stateTime = 0;
    private Direction direction = Direction.FRONT;

    /************ TARGET AND POSITION ************/
    private float targetX;
    private float targetY;
    private float previousX;
    private float previousY;

    /************ MOVEMENT BEHAVIOR ************/
    private final String movementType;
    private float originalX;
    private float originalY;
    private float pauseTime;

    /**
     * Constructs an NPC with the specified attributes.
     *
     * @param fileName     the texture file name for the NPC
     * @param x            the initial X position
     * @param y            the initial Y position
     * @param movementType the movement pattern of the NPC
     * @param type         the type of the NPC
     */
    public NPC(String fileName, float x, float y, String movementType, String type) {
        super(new Texture(Gdx.files.internal(fileName)), x, y);
        npcType = type;
        originalX = x;
        originalY = y;
        this.npcAnimation = new AnimationLoader(fileName);
        this.movementType = movementType;
    }

    /**
     * Renders the NPC using the provided {@link SpriteBatch}.
     *
     * @param batch the sprite batch for rendering
     */
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = npcAnimation.getCurrentAnimation().getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Updates the NPC's state, including movement and animation.
     */
    public void update() {
        if (movementType.equalsIgnoreCase("targeted")) {
            moveTowardsTarget();
        } else if (movementType.equalsIgnoreCase("horizontal")) {
            moveHorizontally();
        } else if (movementType.equalsIgnoreCase("vertical")) {
            moveVertically();
        } else if (movementType.equalsIgnoreCase("in-place")) {
            setStopAnimation();
        }
        stateTime += Gdx.graphics.getDeltaTime();
    }

    /**
     * Moves the NPC towards its target position.
     */
    private void moveTowardsTarget() {
        float delta = Gdx.graphics.getDeltaTime();
        float dx = targetX - getX();
        float dy = targetY - getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            float stepX = (dx / distance) * SPEED * delta;
            float stepY = (dy / distance) * SPEED * delta;

            // Adjust for diagonal movement
            if (Math.abs(dx) > 0 && Math.abs(dy) > 0) {
                stepX /= (float) Math.sqrt(2);
                stepY /= (float) Math.sqrt(2);
            }

            setPosition(getX() + stepX, getY() + stepY);
            updateDirection();
        } else {
            setStopAnimation();
        }
    }

    /**
     * Moves the NPC horizontally between two points.
     */
    private void moveHorizontally() {
        float delta = Gdx.graphics.getDeltaTime();
        float distance = targetX - getX();

        if (Math.abs(distance) > 1) {
            float stepX = (distance > 0 ? 1 : -1) * SPEED * delta;
            setPosition(getX() + stepX, getY());
            updateDirection();
        } else {
            pauseTime += delta;
            if (pauseTime >= 5) {
                targetX = originalX;
                originalX = getX();
                pauseTime = 0;
            } else {
                setStopAnimation();
            }
        }
    }

    /**
     * Moves the NPC vertically between two points.
     */
    private void moveVertically() {
        float delta = Gdx.graphics.getDeltaTime();
        float distance = targetY - getY();

        if (Math.abs(distance) > 1) {
            float stepY = (distance > 0 ? 1 : -1) * SPEED * delta;
            setPosition(getX(), getY() + stepY);
            updateDirection();
        } else {
            pauseTime += delta;
            if (pauseTime >= 5) {
                targetY = originalY;
                originalY = getY();
                pauseTime = 0;
            } else {
                setStopAnimation();
            }
        }
    }

    /**
     * Updates the NPC's direction based on movement.
     */
    private void updateDirection() {
        float currentX = getX();
        float currentY = getY();

        float dx = currentX - previousX;
        float dy = currentY - previousY;

        if (Math.abs(dx) > .1f || Math.abs(dy) > .1f) {
            if (dx > 0 && dy > 0) {
                direction = Direction.BACK_RIGHT;
            } else if (dx < 0 && dy > 0) {
                direction = Direction.BACK_LEFT;
            } else if (dx > 0 && dy < 0) {
                direction = Direction.FRONT_RIGHT;
            } else if (dx < 0 && dy < 0) {
                direction = Direction.FRONT_LEFT;
            } else if (dx > 0) {
                direction = Direction.RIGHT;
            } else if (dx < 0) {
                direction = Direction.LEFT;
            } else if (dy > 0) {
                direction = Direction.BACK;
            } else if (dy < 0) {
                direction = Direction.FRONT;
            }
        }

        switch (direction) {
            case RIGHT, FRONT_RIGHT, BACK_RIGHT -> npcAnimation.setCurrentAnimation("rightWalk");
            case LEFT, FRONT_LEFT, BACK_LEFT -> npcAnimation.setCurrentAnimation("leftWalk");
            case FRONT -> npcAnimation.setCurrentAnimation("frontWalk");
            case BACK -> npcAnimation.setCurrentAnimation("backWalk");
        }
    }

    /**
     * Sets the NPC's animation to an idle state based on its current direction.
     */
    public void setStopAnimation() {
        switch (direction) {
            case RIGHT, FRONT_RIGHT, BACK_RIGHT -> npcAnimation.setCurrentAnimation("rightIdle");
            case LEFT, FRONT_LEFT, BACK_LEFT -> npcAnimation.setCurrentAnimation("leftIdle");
            case FRONT -> npcAnimation.setCurrentAnimation("frontIdle");
            case BACK -> npcAnimation.setCurrentAnimation("backIdle");
        }
    }

    /**
     * Sets the NPC's target position.
     *
     * @param x the target X position
     * @param y the target Y position
     */
    public void setTarget(float x, float y) {
        this.targetX = x;
        this.targetY = y;
    }

    /**
     * Gets the NPC's previous X position.
     *
     * @return the previous X position
     */
    public float getPreviousX() {
        return previousX;
    }

    /**
     * Gets the NPC's previous Y position.
     *
     * @return the previous Y position
     */
    public float getPreviousY() {
        return previousY;
    }

    /**
     * Gets the NPC's type.
     *
     * @return the NPC type
     */
    public String getType() {
        return npcType;
    }

    /**
     * Sets the NPC's previous X position.
     *
     * @param previousX the previous X position
     */
    public void setPreviousX(float previousX) {
        this.previousX = previousX;
    }

    /**
     * Sets the NPC's previous Y position.
     *
     * @param previousY the previous Y position
     */
    public void setPreviousY(float previousY) {
        this.previousY = previousY;
    }
}
