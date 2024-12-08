package io.mygame.entities;

/**
 * Abstract class representing a Janitor, which is a type of NPC in the game.
 * A Janitor has unique characteristics and movement types, inheriting behavior from the NPC class.
 */
public abstract class Janitor extends NPC {
    /**
     * Constructs a Janitor instance with the specified file name, position, and movement type.
     *
     * @param fileName     the file path of the sprite image for the Janitor.
     * @param x            the initial x-coordinate of the Janitor.
     * @param y            the initial y-coordinate of the Janitor.
     * @param movementType the movement type of the Janitor (e.g., targeted, horizontal, etc.).
     */
    public Janitor(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, "Janitor");
    }

    /**
     * Represents a specific type of Janitor with its own sprite.
     * This Janitor is loaded with a unique file name representing the sprite.
     */
    public static class Janitor1 extends Janitor {
        private static final String fileName = "sprites/Janitor.png";

        /**
         * Constructs a Janitor1 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the Janitor1.
         * @param y            the initial y-coordinate of the Janitor1.
         * @param movementType the movement type of the Janitor1 (e.g., targeted, horizontal, etc.).
         */
        public Janitor1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}

