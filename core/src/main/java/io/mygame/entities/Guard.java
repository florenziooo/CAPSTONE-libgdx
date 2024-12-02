package io.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Guard extends NPC {
    public Guard(String fileName, float x, float y, String movementType) {
        super(fileName, x, y, movementType);
    }

    public static class Guard1 extends Guard{
        private final static String fileName = "Sprites/Guard1.png";

        public Guard1(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }

    public static class Guard2 extends Guard{
        private final static String fileName = "Sprites/Guard2.png";

        public Guard2(float x, float y, String movementType) {
            super(fileName, x, y, movementType);
        }
    }
}
