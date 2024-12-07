package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.mygame.common.AnimationLoader;
import io.mygame.enums.Direction;

public abstract class NPC extends Entity {
    private final String npcType;

    private static final float SPEED = 75f;
    private final AnimationLoader npcAnimation;
    private float stateTime = 0;
    private Direction direction = Direction.FRONT;
    private float targetX, targetY;
    private float previousX, previousY;
    private final String movementType;
    private float originalX, originalY;
    private float pauseTime;

    public NPC(String fileName, float x, float y, String movementType, String type) {
        super(new Texture(Gdx.files.internal(fileName)), x, y);

        npcType = type;
        originalX = x;
        originalY = y;
        this.npcAnimation = new AnimationLoader(fileName);
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
        }else if(movementType.equalsIgnoreCase("in-place")){
            setStopAnimation();
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
            setStopAnimation();
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
            pauseTime += delta;
            if(pauseTime >= 5) {
                targetX = originalX;
                originalX = getX();
                pauseTime = 0;
            }else{
                setStopAnimation();
            }
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
            pauseTime += delta;
            if(pauseTime >= 5) {
                targetY = originalY;
                originalY = getY();
                pauseTime = 0;
            }else{
                setStopAnimation();
            }
        }
    }

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
            case RIGHT, FRONT_RIGHT, BACK_RIGHT-> npcAnimation.setCurrentAnimation("rightWalk");
            case LEFT, FRONT_LEFT, BACK_LEFT -> npcAnimation.setCurrentAnimation("leftWalk");
            case FRONT -> npcAnimation.setCurrentAnimation("frontWalk");
            case BACK -> npcAnimation.setCurrentAnimation("backWalk");
        }
    }

    public void setStopAnimation(){
        switch (direction) {
            case RIGHT, FRONT_RIGHT, BACK_RIGHT -> npcAnimation.setCurrentAnimation("rightIdle");
            case LEFT, FRONT_LEFT, BACK_LEFT -> npcAnimation.setCurrentAnimation("leftIdle");
            case FRONT -> npcAnimation.setCurrentAnimation("frontIdle");
            case BACK -> npcAnimation.setCurrentAnimation("backIdle");
        }
    }

    public void setTarget(float x, float y) {
        this.targetX = x;
        this.targetY = y;
    }

    public float getPreviousX() {
        return previousX;
    }

    public float getPreviousY() {
        return previousY;
    }

    public String getType() {
        return npcType;
    }

    public void setPreviousX(float previousX) {
        this.previousX = previousX;
    }

    public void setPreviousY(float previousY) {
        this.previousY = previousY;
    }
}
