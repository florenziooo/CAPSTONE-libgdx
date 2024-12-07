package io.mygame.factories;

import io.mygame.entities.*;

import java.util.ArrayList;
import java.util.List;

public class EntityFactory {
    public static List<NPC> createNPCs() {
        List<NPC> npcs = new ArrayList<>();

        npcs.add(new Guard.Guard1(1790, 287, "in-place"));
        npcs.add(new Guard.Guard2(1919, 233, "in-place"));
        npcs.add(new Guard.Guard2(1546, 735, "in-place"));
        npcs.add(new Guard.Guard1(1447, 1792, "in-place"));
        npcs.add(new Guard.Guard2(1409, 2516, "in-place"));
        npcs.add(new Guard.Guard1(1305, 2475, "in-place"));

        // 6
        npcs.add(new Student.CollegeMale2(1370, 180, "in-place"));
        npcs.add(new Student.CollegeMale1(1096, 654, "horizontal"));
        npcs.add(new Student.CollegeMale1(1805, 1024, "in-place"));
        npcs.add(new Student.CollegeMale2(1329, 1762, "in-place"));
        npcs.add(new Student.CollegeMale1(1185, 1691, "in-place"));
        npcs.add(new Student.CollegeMale1(986, 2185, "vertical"));
        npcs.add(new Student.CollegeMale2(1439, 2342, "in-place"));
        npcs.add(new Student.CollegeMale1(1506, 2250, "in-place"));

        // 14
        npcs.add(new Student.CollegeFemale1(1508, 204, "horizontal")); // h
        npcs.add(new Student.CollegeFemale2(1701, 351, "in-place"));
        npcs.add(new Student.CollegeFemale1(942, 1160, "vertical"));
        npcs.add(new Student.CollegeFemale1(1769, 781, "in-place")); // v
        npcs.add(new Student.CollegeFemale2(1922, 1420, "vertical")); // v
        npcs.add(new Student.CollegeFemale2(1502, 1533, "in-place"));
        npcs.add(new Student.CollegeFemale2(1708, 2473, "in-place"));
        npcs.add(new Student.CollegeFemale1(1772, 1753, "in-place"));

        // 22
        npcs.add(new Teacher.MaleTeacher(1635, 563, "in-place"));
        npcs.add(new Teacher.FemaleTeacher(1560, 924, "in-place"));

        // 24
        npcs.add(new Janitor.Janitor1(901, 607, "vertical")); // v
        npcs.add(new Janitor.Janitor1(900, 1982, "vertical"));
        npcs.add(new Janitor.Janitor1(1562, 2137, "in-place"));

        // 27
        npcs.add(new Pet.Pet1(1708, 2215, "vertical")); // v
        npcs.add(new Pet.Pet2(1914, 1885, "in-place")); // v
        npcs.add(new Pet.Pet1(1974, 520, "vertical")); // v

        setTargets(npcs);

        return npcs;
    }

    public static void setTargets(List<NPC> npcs){
        try {
            NPC npc;

            npc = npcs.get(7);
            npc.setTarget(npc.getX() + 115, npc.getY());

            npc = npcs.get(14);
            npc.setTarget(npc.getX() + 100, npc.getY());

            npc = npcs.get(11);
            npc.setTarget(npc.getX() - 155, npc.getY());

            npc = npcs.get(16);
            npc.setTarget(npc.getX() + 150, npc.getY());

            npc = npcs.get(18);
            npc.setTarget(npc.getX(), npc.getY() + 110);

            npc = npcs.get(24);
            npc.setTarget(npc.getX(), npc.getY() + 350);

            npc = npcs.get(25);
            npc.setTarget(npc.getX(), npc.getY() - 100);

            npc = npcs.get(27);
            npc.setTarget(npc.getX(), npc.getY() - 130);

            npc = npcs.get(29);
            npc.setTarget(npc.getX(), npc.getY() - 200);

//            npc = npcs.get(4);
//            npc.setTarget(npc.getX() + 100, npc.getY());
//
//            npc = npcs.get(6);
//            npc.setTarget(npc.getX(), npc.getY() + 1000);
//
//            npc = npcs.get(7);
//            npc.setTarget(npc.getX(), npc.getY() + 1450);
        } catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("Error: Something went wrong when trying to set NPC targets.");
        }
    }
}
