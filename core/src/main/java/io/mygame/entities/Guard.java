package io.mygame.entities;

/**
 * Abstract class representing a Guard, which is a type of NPC in the game.
 * A Guard has unique characteristics and movement types, inheriting behavior from the NPC class.
 */
public abstract class Guard extends NPC {
    /**
     * Constructs a Guard instance with the specified file name, position, and movement type.
     *
     * @param fileName     the file path of the sprite image for the Guard.
     * @param x            the initial x-coordinate of the Guard.
     * @param y            the initial y-coordinate of the Guard.
     * @param movementType the movement type of the Guard (e.g., targeted, horizontal, etc.).
     */
    public Guard(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, "Guard");
    }

    /**
     * Represents a specific type of Guard with its own sprite.
     * This Guard uses the file `Sprites/Guard1.png` as its sprite.
     */
    public static class Guard1 extends Guard {
        private static final String fileName = "Sprites/Guard1.png";

        /**
         * Constructs a Guard1 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the Guard1.
         * @param y            the initial y-coordinate of the Guard1.
         * @param movementType the movement type of the Guard1 (e.g., targeted, horizontal, etc.).
         */
        public Guard1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    /**
     * Represents a specific type of Guard with its own sprite.
     * This Guard uses the file `Sprites/Guard2.png` as its sprite.
     */
    public static class Guard2 extends Guard {
        private static final String fileName = "Sprites/Guard2.png";

        /**
         * Constructs a Guard2 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the Guard2.
         * @param y            the initial y-coordinate of the Guard2.
         * @param movementType the movement type of the Guard2 (e.g., targeted, horizontal, etc.).
         */
        public Guard2(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}
