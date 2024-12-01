package io.mygame.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Student extends NPC{
    public Student(TextureRegion texture, float x, float y, boolean canWalk, String movementType) {
        super(texture, x, y, canWalk, movementType);
    }
}
