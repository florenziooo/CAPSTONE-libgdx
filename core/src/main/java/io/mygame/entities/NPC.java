package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.mygame.common.AnimationLoader;
import io.mygame.enums.Direction;

public class NPC extends GameObject {
    private static final float SPEED = 100f;
    private final boolean canWalk;
    private boolean moving;
    private final AnimationLoader npcAnimation;
    private float stateTime = 0;
    private Direction direction = Direction.FRONT;
    private float targetX, targetY;
    private float currentX, currentY;
    private float previousX, previousY;
    private final Vector2 movement = new Vector2();
    private final String movementType;
    private float originalX, originalY;

    public NPC(TextureRegion texture, float x, float y, boolean canWalk, String movementType) {
        super(texture, x, y);
        originalX = x;
        originalY = y;
        this.npcAnimation = new AnimationLoader("Sprites/CITBOY.png", "npc");
        this.canWalk = canWalk;
        this.moving = canWalk;
        this.movementType = movementType;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = npcAnimation.getCurrentAnimation().getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void update() {
        if(movementType.equalsIgnoreCase("targeted")) {
            moveTowardsTarget();
        }else if(movementType.equalsIgnoreCase("horizontal")) {
            moveHorizontally();
        }else if(movementType.equalsIgnoreCase("vertical")) {
            moveVertically();
        }
        stateTime += Gdx.graphics.getDeltaTime();
    }

    private void moveTowardsTarget() {
        float delta = Gdx.graphics.getDeltaTime();
        float dx = targetX - getX();
        float dy = targetY - getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {

            float stepX = (dx / distance) * SPEED * delta;
            float stepY = (dy / distance) * SPEED * delta;

            // Set diagonal movement adjustment
            if (Math.abs(dx) > 0 && Math.abs(dy) > 0) {
                stepX /= (float) Math.sqrt(2);
                stepY /= (float) Math.sqrt(2);
            }

            setPosition(getX() + stepX, getY() + stepY);

            updateDirection();
        } else {
            moving = false;
        }
    }

    private void moveHorizontally() {
        float delta = Gdx.graphics.getDeltaTime();
        float distance = targetX - getX();

        if (Math.abs(distance) > 1) {
            float stepX = (distance > 0 ? 1 : -1) * SPEED * delta;
            setPosition(getX() + stepX, getY());
            updateDirection();
        } else {
            targetX = originalX;
            originalX = getX();
        }
    }

    private void moveVertically() {
        float delta = Gdx.graphics.getDeltaTime();
        float distance = targetY - getY();

        if (Math.abs(distance) > 1) {
            float stepY = (distance > 0 ? 1 : -1) * SPEED * delta;
            setPosition(getX(), getY() + stepY);
            updateDirection();
        } else {
            targetY = originalY;
            originalY = getY();
        }
    }

    private void updateDirection() {

        currentX = getX();
        currentY = getY();

        float dx = currentX - previousX;
        float dy = currentY - previousY;

        if (Math.abs(dx) > 0.01f || Math.abs(dy) > 0.01f) { // To handle small changes
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
            case RIGHT, FRONT_RIGHT, BACK_RIGHT-> npcAnimation.setCurrentAnimation("rightWalk");
            case LEFT, FRONT_LEFT, BACK_LEFT -> npcAnimation.setCurrentAnimation("leftWalk");
            case FRONT -> npcAnimation.setCurrentAnimation("frontWalk");
            case BACK -> npcAnimation.setCurrentAnimation("backWalk");
        }
    }


    public void setTarget(float x, float y) {
        this.targetX = x;
        this.targetY = y;
        this.moving = true;
    }

    public boolean isCanWalk() {
        return canWalk;
    }

    public boolean isMoving() {
        return moving;
    }

    public float getPreviousX() {
        return previousX;
    }

    public float getPreviousY() {
        return previousY;
    }

    public void setPreviousX(float previousX) {
        this.previousX = previousX;
    }

    public void setPreviousY(float previousY) {
        this.previousY = previousY;
    }

    public void setMovement(float x, float y){
        movement.x = x;
        movement.y = y;
    }

}
