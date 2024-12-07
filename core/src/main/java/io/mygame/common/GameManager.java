package io.mygame.common;

import io.mygame.datahandler.Serializable;

import java.util.HashMap;

public class GameManager implements Serializable {
    private static GameManager instance;
    private String playerName;
    private int npcFound;
    private int buildingsFound;
    private float volume;
    private transient SoundManager soundManager;
    private HashMap<String, Boolean> areasFound;
    private HashMap<String, Boolean> npcInteracted;

    private GameManager() {
        resetDefaultValues();
    }

    public static GameManager getInstance() {
        if(instance == null) instance = new GameManager();
        return instance;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNpcFound() {
        return npcFound;
    }

    public int getBuildingsFound() {
        return buildingsFound;
    }

    public float getVolume() {
        return volume;
    }

    public SoundManager getSoundManager() { return soundManager; }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setNpcFound(int npcFound) {
        this.npcFound = npcFound;
    }

    public void setBuildingFound(int buildingsFound) {
        this.buildingsFound = buildingsFound;
    }

    public void setSoundManager(SoundManager soundManager) { this.soundManager = soundManager; }

    public void setVolume(float volume) {
        this.volume = volume;
        if (soundManager != null) {
            soundManager.setGlobalVolume(this.volume);
        }
    }

    public void setAreaFound(String key) {
        areasFound.put(key, true);
    }

    public void setAreasFound(HashMap<String, Boolean> areasFound) {
        this.areasFound = areasFound;
    }

    public void setNpcInteracted(String key) {
        if(!npcInteracted.get(key)) npcFound++;
        npcInteracted.put(key, true);
    }

    public void setNpcsFound(HashMap<String, Boolean> npcInteracted) {
        this.areasFound = areasFound;
    }

    public void increaseNpcFound() {
        npcFound++;
    }

    public void increaseBuildingsFound() {
        buildingsFound++;
    }

    public boolean getAreaFound(String key) {
        Boolean value = areasFound.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Key: " + key + " not found in HashMap as part of the discoverable areas");
        }
        return value;
    }

    public HashMap<String, Boolean> getNpcInteracted() {
        return npcInteracted;
    }

    public HashMap<String, Boolean> getAreasFound() {
        return areasFound;
    }

    public void resetDefaultValues() {
        playerName = null;
        npcFound = 0;
        buildingsFound = 0;
        volume = 1.0f;

        areasFound = new HashMap<>();
        areasFound.put("NGE", false);
        areasFound.put("RTL", false);
        areasFound.put("GLE", false);
        areasFound.put("SAL", false);
        areasFound.put("Library", false);
        areasFound.put("Espasyo", false);
        areasFound.put("Patio", false);
        areasFound.put("ACAD", false);
        areasFound.put("ELEM", false);
        areasFound.put("Gym", false);
        areasFound.put("Canteen", false);
        areasFound.put("Chapel", false);

        npcInteracted = new HashMap<>();
        npcInteracted.put("Janitor", false);
        npcInteracted.put("Guard", false);
        npcInteracted.put("Teacher", false);
        npcInteracted.put("MaleStudent", false);
        npcInteracted.put("FemaleStudent", false);
        npcInteracted.put("Pet", false);
    }
}
