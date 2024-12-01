package io.mygame.factories;

import io.mygame.entities.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFactory {
    private static Texture npcTexture = new Texture(Gdx.files.internal("Sprites/CITBOY.png")); // Updated path
    private static TextureRegion textureRegion = new TextureRegion(npcTexture, 0, 0, 16, 32);

    public static List<NPC> createNPCs() {
        List<NPC> npcs = new ArrayList<>();

        npcs.add(new Teacher(textureRegion, 384, 256, true, "horizontal"));
        npcs.add(new Teacher(textureRegion, 64,64, false, "in-place"));
        npcs.add(new Student(textureRegion, 128, 128,true,"vertical"));
        npcs.add(new Student(textureRegion, 352, 96, false, "in-place"));
        npcs.add(new Guard(textureRegion, 214, 256, true, "targeted"));
        npcs.add(new Guard(textureRegion, 512,0, false, "in-place"));
        npcs.add(new Janitor(textureRegion, 400, 128,true, "targeted"));
        npcs.add(new Janitor(textureRegion, 128, 256, false, "in-place"));

        NPC npc = npcs.get(0);
        npc.setTarget(npc.getX() - 100, npc.getY());
        npc = npcs.get(2);
        npc.setTarget(npc.getX(), npc.getY() + 64);
        return npcs;
    }


    public static void dispose() {
        npcTexture.dispose();
    }
}
