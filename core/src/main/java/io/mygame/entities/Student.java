package io.mygame.entities;

public abstract class Student extends NPC {
    private final static String textureDimensions = "8x6";
    public Student(String fileName, float x, float y, String movementType, String npcType) {
        super(fileName, x, y, movementType, textureDimensions, npcType);
    }

    public static class CollegeMale1 extends Student{
        private final static String fileName = "Sprites/CollegeMale1.png";

        public CollegeMale1(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "MaleStudent");
        }
    }

    public static class CollegeMale2 extends Student{
        private final static String fileName = "Sprites/CollegeMale2.png";

        public CollegeMale2(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "MaleStudent");
        }
    }

    public static class CollegeFemale1 extends Student{
        private final static String fileName = "Sprites/CollegeFemale1.png";

        public CollegeFemale1(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "FemaleStudent");
        }
    }

    public static class CollegeFemale2 extends Student{
        private final static String fileName = "Sprites/CollegeFemale2.png";

        public CollegeFemale2(float x, float y, String movementType) {
            super(fileName, x, y, movementType, "FemaleStudent");
        }
    }
}
