/**
 * Abstract class representing a Teacher, which is a type of NPC in the game.
 * A Teacher has unique characteristics and movement types, inheriting behavior from the NPC class.
 */
package io.mygame.entities;

public abstract class Teacher extends NPC {
    /**
     * Constructs a Teacher instance with the specified file name, position, and movement type.
     *
     * @param fileName     the file path of the sprite image for the Teacher.
     * @param x            the initial x-coordinate of the Teacher.
     * @param y            the initial y-coordinate of the Teacher.
     * @param movementType the movement type of the Teacher (e.g., targeted, horizontal, etc.).
     */
    public Teacher(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, "Teacher");
    }

    /**
     * Represents a specific male teacher with its own sprite.
     * This teacher uses the file `Sprites/MaleTeacher.png` as its sprite.
     */
    public static class MaleTeacher extends Teacher {
        private static final String fileName = "Sprites/MaleTeacher.png";

        /**
         * Constructs a MaleTeacher instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the MaleTeacher.
         * @param y            the initial y-coordinate of the MaleTeacher.
         * @param movementType the movement type of the MaleTeacher (e.g., targeted, horizontal, etc.).
         */
        public MaleTeacher(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    /**
     * Represents a specific female teacher with its own sprite.
     * This teacher uses the file `Sprites/FemaleTeacher.png` as its sprite.
     */
    public static class FemaleTeacher extends Teacher {
        private static final String fileName = "Sprites/FemaleTeacher.png";

        /**
         * Constructs a FemaleTeacher instance with the specified position and movement type.
         *
         * @param x            the initial x-coordinate of the FemaleTeacher.
         * @param y            the initial y-coordinate of the FemaleTeacher.
         * @param movementType the movement type of the FemaleTeacher (e.g., targeted, horizontal, etc.).
         */
        public FemaleTeacher(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}
