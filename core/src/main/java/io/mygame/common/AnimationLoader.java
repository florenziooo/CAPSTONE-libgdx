package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * The AnimationLoader class loads a sprite sheet and manages character animations for different
 * movement states (idle, walking) and directions (front, right, left, back).
 * It splits the sprite sheet into frames and provides methods to switch and retrieve the current animation.
 * The constructor initializes animations from the provided sprite sheet path.
 */
public class AnimationLoader {
    /************ PATH ************/
    private final String path;

    /************ ANIMATIONS ************/
    private Animation<TextureRegion> frontIdleAnimation, rightIdleAnimation, leftIdleAnimation, backIdleAnimation;
    private Animation<TextureRegion> frontWalkAnimation, rightWalkAnimation, leftWalkAnimation, backWalkAnimation;
    private Animation<TextureRegion> currentAnimation;

    /**
     * Constructor that initializes the AnimationLoader with the given path
     * and calls the loadSprite method to load animations.
     * @param path The path to the sprite sheet.
     */
    public AnimationLoader(String path) {
        this.path = path;
        loadSprite();
    }

    /**
     * Loads the sprite sheet and initializes the animations.
     */
    private void loadSprite() {
        try {
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
        } catch (GdxRuntimeException e) {
            System.err.println("Failed to load the texture. Ensure the file path is correct: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid sprite sheet dimensions for splitting: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("A null value was encountered. Check the 'path' variable or texture operations: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Extracts animation frames from the sprite sheet.
     * @param tmpFrames 2D array of texture regions split from the sprite sheet.
     * @param column Column index of the desired animation in the sprite sheet.
     * @return Animation created from the extracted frames.
     */
    private Animation<TextureRegion> extractFrames(TextureRegion[][] tmpFrames, int column) {
        TextureRegion[] frames = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {
            if (i < tmpFrames.length) {
                frames[i] = tmpFrames[i][column];
            }
        }

        return new Animation<>(0.2f, frames);
    }

    /**
     * Sets the current animation based on the provided animation name.
     * @param animation The name of the animation to set.
     */
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

    /**
     * Gets the currently active animation.
     * @return The current animation.
     */
    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
