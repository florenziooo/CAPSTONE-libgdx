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

        npcs.add(new Teacher(textureRegion, 2995, 316, true, "horizontal"));
        npcs.add(new Teacher(textureRegion, 3100,400, false, "in-place"));
        npcs.add(new Student(textureRegion, 3180, 202,true,"vertical"));
        npcs.add(new Student(textureRegion, 3180, 400, false, "in-place"));
        npcs.add(new Guard(textureRegion, 3230, 514, true, "targeted"));
        npcs.add(new Guard(textureRegion, 3200,500, false, "in-place"));
        npcs.add(new Janitor(textureRegion, 3011, 400, false, "in-place"));

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
