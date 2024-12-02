package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AnimationLoader {
    private final String path;

    private Animation<TextureRegion> frontIdleAnimation, rightIdleAnimation, leftIdleAnimation, backIdleAnimation;
    private Animation<TextureRegion> frontWalkAnimation, rightWalkAnimation, leftWalkAnimation, backWalkAnimation;
    private Animation<TextureRegion> currentAnimation;

    public AnimationLoader(String path, String numberOfColsAndRows) {
        this.path = path;
        if(numberOfColsAndRows.equals("8x6")) {
            load8x6();
        }else if(numberOfColsAndRows.equals("4x3")){
            load4x3();
        }
    }

    private void load8x6() {
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

    private void load4x3() {
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

    private Animation<TextureRegion> extractFrames(TextureRegion[][] tmpFrames, int column) {
        TextureRegion[] frames = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {
            if (i < tmpFrames.length) {
                frames[i] = tmpFrames[i][column];
            }
        }

        return new Animation<>(0.2f, frames);
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
