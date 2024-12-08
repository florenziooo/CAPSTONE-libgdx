package io.mygame.entities;

/**
 * Abstract class representing a Pet, which is a type of NPC in the game.
 * A Pet has unique characteristics and movement types, inheriting behavior from the NPC class.
 */
public abstract class Pet extends NPC {
    /**
     * Constructs a Pet instance with the specified file name, position, and movement type.
     *
     * @param fileName     the file path of the sprite image for the Pet.
     * @param x            the initial x-coordinate of the Pet.
     * @param y            the initial y-coordinate of the Pet.
     * @param movementType the movement type of the Pet (e.g., targeted, horizontal, etc.).
     */
    public Pet(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, "Pet");
    }

    /**
     * Represents a specific type of Pet with its own sprite.
     * This Pet is loaded with a unique file name representing the sprite.
     */
    public static class Pet1 extends Pet {
        private static final String fileName = "sprites/BrunoCat.png";

        /**
         * Constructs a Pet1 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the Pet1.
         * @param y            the initial y-coordinate of the Pet1.
         * @param movementType the movement type of the Pet1 (e.g., targeted, horizontal, etc.).
         */
        public Pet1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    /**
     * Represents another specific type of Pet with its own sprite.
     * This Pet is loaded with a unique file name representing the sprite.
     */
    public static class Pet2 extends Pet {
        private static final String fileName = "Sprites/BrunoCat.png";

        /**
         * Constructs a Pet2 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the Pet2.
         * @param y            the initial y-coordinate of the Pet2.
         * @param movementType the movement type of the Pet2 (e.g., targeted, horizontal, etc.).
         */
        public Pet2(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}

