package io.mygame.factories;

import io.mygame.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class that creates and initializes NPCs (guards, students, teachers, janitors, pets) with movement behaviors and target positions.
 */
public class NPCFactory {

    /**
     * Creates and returns a list of NPCs with predefined positions and movement types (e.g., in-place, vertical, horizontal).
     * NPC types include guards, students, teachers, janitors, and pets, each with specific behaviors and roles.
     *
     * @return A list of NPC entities with initial positions, movement types, and roles.
     */
    public static List<NPC> createNPCs() {
        List<NPC> npcs = new ArrayList<>();

        // Adding Guard NPCs
        npcs.add(new Guard.Guard1(1790, 287, "in-place"));
        npcs.add(new Guard.Guard2(1919, 233, "in-place"));
        npcs.add(new Guard.Guard2(1546, 735, "in-place"));
        npcs.add(new Guard.Guard1(1466, 1953, "vertical"));
        npcs.add(new Guard.Guard2(1409, 2516, "in-place"));
        npcs.add(new Guard.Guard1(1305, 2475, "in-place"));

        // Adding Male Student NPCs
        npcs.add(new Student.CollegeMale2(1154, 1457, "horizontal"));
        npcs.add(new Student.CollegeMale1(1014, 654, "horizontal"));
        npcs.add(new Student.CollegeMale1(1805, 1024, "in-place"));
        npcs.add(new Student.CollegeMale2(1329, 1762, "in-place"));
        npcs.add(new Student.CollegeMale1(1185, 1691, "in-place"));
        npcs.add(new Student.CollegeMale1(986, 2185, "vertical"));
        npcs.add(new Student.CollegeMale2(1439, 2342, "in-place"));
        npcs.add(new Student.CollegeMale1(1506, 2250, "in-place"));

        // Adding Female Student NPCs
        npcs.add(new Student.CollegeFemale1(1807, 226, "in-place"));
        npcs.add(new Student.CollegeFemale2(1701, 351, "in-place"));
        npcs.add(new Student.CollegeFemale1(942, 1160, "vertical"));
        npcs.add(new Student.CollegeFemale1(1769, 781, "in-place"));
        npcs.add(new Student.CollegeFemale2(1835, 1470, "vertical"));
        npcs.add(new Student.CollegeFemale2(1502, 1533, "in-place"));
        npcs.add(new Student.CollegeFemale2(1736, 2470, "in-place"));
        npcs.add(new Student.CollegeFemale1(1772, 1753, "in-place"));

        // Adding Teacher NPCs
        npcs.add(new Teacher.MaleTeacher(1385, 681, "in-place"));
        npcs.add(new Teacher.FemaleTeacher(1554, 900, "in-place"));

        // Adding Janitor NPCs
        npcs.add(new Janitor.Janitor1(901, 607, "vertical"));
        npcs.add(new Janitor.Janitor1(900, 1982, "vertical"));
        npcs.add(new Janitor.Janitor1(1562, 2137, "in-place"));

        // Adding Pet NPCs
        npcs.add(new Pet.Pet1(1708, 2215, "vertical"));
        npcs.add(new Pet.Pet2(1914, 1885, "in-place"));
        npcs.add(new Pet.Pet1(1974, 520, "vertical"));

        // Set target positions for specific NPCs
        setTargets(npcs);

        return npcs;
    }

    /**
     * Sets target positions for specific NPCs based on their initial coordinates and movement type.
     *
     * @param npcs A list of NPCs to update target positions.
     * @throws RuntimeException If an index out of bounds error occurs while setting targets.
     */
    public static void setTargets(List<NPC> npcs){
        try {
            NPC npc;

            npc = npcs.get(3);
            npc.setTarget(npc.getX(), npc.getY() - 200);

            npc = npcs.get(6);
            npc.setTarget(npc.getX() + 167, npc.getY());

            npc = npcs.get(7);
            npc.setTarget(npc.getX() + 115, npc.getY());

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
        } catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("Error: Something went wrong when trying to set NPC targets.");
        }
    }
}
