package io.mygame.common;

import io.mygame.datahandler.Serializable;

public class GameManager implements Serializable {
    private static GameManager instance;
    private String playerName;
    private int npcFound;
    private int buildingsFound;
    private float volume;
    private transient SoundManager soundManager;

    private GameManager() {
        playerName = null;
        npcFound = 0;
        buildingsFound = 0;
        volume = 1.0f;
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

    public void increaseNpcFound() {
        npcFound++;
    }

    public void increaseBuildingsFound() {
        buildingsFound++;
    }

    public void resetDefaultValues() {
        playerName = null;
        npcFound = 0;
        buildingsFound = 0;
        volume = 1.0f;
    }
}
