package io.mygame.entities;

public abstract class Janitor extends NPC {
    public Janitor(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType, "Janitor");
    }

    public static class Janitor1 extends Janitor{
        private final static String fileName = "sprites/Janitor.png";

        public Janitor1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}