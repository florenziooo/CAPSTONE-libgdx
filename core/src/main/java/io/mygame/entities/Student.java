package io.mygame.entities;

/**
 * Abstract class representing a Student, which is a type of NPC in the game.
 * A Student has unique characteristics and movement types, inheriting behavior from the NPC class.
 */
public abstract class Student extends NPC {
    /**
     * Constructs a Student instance with the specified file name, position, movement type, and NPC type.
     *
     * @param fileName     the file path of the sprite image for the Student.
     * @param x            the initial x-coordinate of the Student.
     * @param y            the initial y-coordinate of the Student.
     * @param movementType the movement type of the Student (e.g., targeted, horizontal, etc.).
     * @param npcType      the type of Student (e.g., MaleStudent, FemaleStudent).
     */
    public Student(String fileName, float x, float y, String movementType, String npcType) {
        super(fileName, x, y, movementType, npcType);
    }

    /**
     * Represents a specific male college student with its own sprite.
     * This student uses the file `Sprites/CollegeMale1.png` as its sprite.
     */
    public static class CollegeMale1 extends Student {
        private static final String fileName = "Sprites/CollegeMale1.png";

        /**
         * Constructs a CollegeMale1 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of CollegeMale1.
         * @param y            the initial y-coordinate of CollegeMale1.
         * @param movementType the movement type of CollegeMale1 (e.g., targeted, horizontal, etc.).
         */
        public CollegeMale1(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "MaleStudent");
        }
    }

    /**
     * Represents a specific male college student with its own sprite.
     * This student uses the file `Sprites/CollegeMale2.png` as its sprite.
     */
    public static class CollegeMale2 extends Student {
        private static final String fileName = "Sprites/CollegeMale2.png";

        /**
         * Constructs a CollegeMale2 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of CollegeMale2.
         * @param y            the initial y-coordinate of CollegeMale2.
         * @param movementType the movement type of CollegeMale2 (e.g., targeted, horizontal, etc.).
         */
        public CollegeMale2(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "MaleStudent");
        }
    }

    /**
     * Represents a specific female college student with its own sprite.
     * This student uses the file `Sprites/CollegeFemale1.png` as its sprite.
     */
    public static class CollegeFemale1 extends Student {
        private static final String fileName = "Sprites/CollegeFemale1.png";

        /**
         * Constructs a CollegeFemale1 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of CollegeFemale1.
         * @param y            the initial y-coordinate of CollegeFemale1.
         * @param movementType the movement type of CollegeFemale1 (e.g., targeted, horizontal, etc.).
         */
        public CollegeFemale1(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "FemaleStudent");
        }
    }

    /**
     * Represents a specific female college student with its own sprite.
     * This student uses the file `Sprites/CollegeFemale2.png` as its sprite.
     */
    public static class CollegeFemale2 extends Student {
        private static final String fileName = "Sprites/CollegeFemale2.png";

        /**
         * Constructs a CollegeFemale2 instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of CollegeFemale2.
         * @param y            the initial y-coordinate of CollegeFemale2.
         * @param movementType the movement type of CollegeFemale2 (e.g., targeted, horizontal, etc.).
         */
        public CollegeFemale2(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "FemaleStudent");
        }
    }
}
