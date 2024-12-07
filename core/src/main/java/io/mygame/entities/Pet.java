package io.mygame.entities;

public abstract class Pet extends NPC{
    private final static String textureDimensions = "8x6";
    public Pet(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, textureDimensions, "Pet");
    }

    public static class Pet1 extends Pet{
        private final static String fileName = "sprites/BrunoCat.png";

        public Pet1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    public static class Pet2 extends Pet{
        private final static String fileName = "Sprites/BrunoCat.png";

        public Pet2(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}
