package io.mygame.factories;

import io.mygame.entities.*;

import java.util.ArrayList;
import java.util.List;

public class NPCFactory {
    public static List<NPC> createNPCs() {
        List<NPC> npcs = new ArrayList<>();

        npcs.add(new Student.CollegeMale1(2995, 316, "horizontal"));
        npcs.add(new Student.CollegeMale2(3100, 400, "in-place"));
        npcs.add(new Student.CollegeFemale1(3180, 202, "vertical"));
        npcs.add(new Student.CollegeFemale2(3180, 400, "in-place"));
        npcs.add(new Teacher.MaleTeacher(3230, 514, "targeted"));
        npcs.add(new Teacher.FemaleTeacher(3200, 500, "in-place"));
        npcs.add(new Guard.Guard1(2980, 600, "targeted"));
        npcs.add(new Guard.Guard2(3011, 400, "in-place"));
        npcs.add(new Pet.Pet1(2304, 200, "in-place"));
        npcs.add(new Pet.Pet2(2764, 453, "horizontal"));

        setTargets(npcs);

        return npcs;
    }

    public static void setTargets(List<NPC> npcs){
        NPC npc = npcs.get(0);
        npc.setTarget(npc.getX() - 100, npc.getY());
        npc = npcs.get(2);
        npc.setTarget(npc.getX(), npc.getY() + 64);
        npc = npcs.get(9);
        npc.setTarget(npc.getX()+64, npc.getY());
    }
}
