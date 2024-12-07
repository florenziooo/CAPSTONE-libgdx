package io.mygame.entities;

public abstract class Teacher extends NPC {
    private final static String textureDimensions = "8x6";
    public Teacher(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, textureDimensions, "Teacher");
    }

    public static class MaleTeacher extends Teacher{
        private final static String fileName = "Sprites/MaleTeacher.png";

        public MaleTeacher(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    public static class FemaleTeacher extends Teacher{
        private final static String fileName = "Sprites/FemaleTeacher.png";

        public FemaleTeacher(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}
