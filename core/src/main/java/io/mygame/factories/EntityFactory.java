package io.mygame.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.mygame.entities.NPC;

public class EntityFactory {
    public static NPC createNPC(String texturePath, int x, int y) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 16, 32);
        return new NPC(textureRegion, x, y);
    }
}
