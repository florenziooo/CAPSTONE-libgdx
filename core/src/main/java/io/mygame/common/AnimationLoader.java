package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {
    private final String path;

    private Animation<TextureRegion> frontIdleAnimation, rightIdleAnimation, leftIdleAnimation, backIdleAnimation;
    private Animation<TextureRegion> frontWalkAnimation, rightWalkAnimation, leftWalkAnimation, backWalkAnimation;
    private Animation<TextureRegion> currentAnimation;

    public AnimationLoader(String path) {
        this.path = path;
        load();
    }

    private void load() {
        Texture spriteSheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet, 16, 32);

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

    private Animation<TextureRegion> extractFrames(TextureRegion[][] tmpFrames, int column) {
        TextureRegion[] frames = new TextureRegion[6]; // Allocate array for the specified number of frames

        for (int i = 0; i < 6; i++) {
            if (i < tmpFrames.length) {
                frames[i] = tmpFrames[i][column]; // Extract the frame from the specified column
            }
        }

        return new Animation<>(0.2f, frames); // Create an animation with the extracted frames
    }

    public void setCurrentAnimation(String animation) {
        switch(animation) {
            case "frontIdle":
                currentAnimation = frontIdleAnimation;
                break;
            case "rightIdle":
                currentAnimation = rightIdleAnimation;
                break;
            case "leftIdle":
                currentAnimation = leftIdleAnimation;
                break;
            case "backIdle":
                currentAnimation = backIdleAnimation;
                break;
            case "frontWalk":
                currentAnimation = frontWalkAnimation;
                break;
            case "rightWalk":
                currentAnimation = rightWalkAnimation;
                break;
            case "leftWalk":
                currentAnimation = leftWalkAnimation;
                break;
            case "backWalk":
                currentAnimation = backWalkAnimation;
                break;
        }
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
