package io.mygame.factories;

import io.mygame.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFactory {
    private static Texture npcTexture = new Texture(Gdx.files.internal("Sprites/CITBOY.png")); // Updated path
    private static TextureRegion textureRegion = new TextureRegion(npcTexture, 0, 0, 16, 32);

    public static List<NPC> createNPCs() {
        List<NPC> npcs = new ArrayList<>();

        npcs.add(new Teacher(textureRegion, 384, 256, true));
        npcs.add(new Teacher(textureRegion, 64,64, false));
        npcs.add(new Student(textureRegion, 128, 128,true));
        npcs.add(new Student(textureRegion, 352, 96, false));
        npcs.add(new Guard(textureRegion, 214, 256, true));
        npcs.add(new Guard(textureRegion, 512,0, false));
        npcs.add(new Janitor(textureRegion, 256, 128,true));
        npcs.add(new Janitor(textureRegion, 128, 256, false));
        return npcs;
    }


    public static void dispose() {
        npcTexture.dispose();
    }
}
