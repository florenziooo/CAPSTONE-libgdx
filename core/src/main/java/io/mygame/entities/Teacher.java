package io.mygame.entities;

public abstract class Teacher extends NPC {
    public Teacher(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType);
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
