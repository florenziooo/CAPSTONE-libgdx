package io.mygame.entities;

public abstract class Pet extends NPC{
    private final static String textureDimensions = "4x3";
    public Pet(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, textureDimensions);
    }

    public static class Pet1 extends Pet{
        private final static String fileName = "Sprites/Pet1.png";

        public Pet1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    public static class Pet2 extends Pet{
        private final static String fileName = "Sprites/Pet2.png";

        public Pet2(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}
