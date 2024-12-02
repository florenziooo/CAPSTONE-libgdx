package io.mygame.common;

import io.mygame.datahandler.Serializable;

public class GameManager implements Serializable {
    private static GameManager instance;
    private String playerName;
    private int npcFound;
    private int buildingsFound;
    private float volume;

    private GameManager() {
        playerName = null;
        npcFound = 0;
        buildingsFound = 0;
        volume = 0;
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setNpcFound(int npcFound) {
        this.npcFound = npcFound;
    }

    public void setBuildingFound(int buildingsFound) {
        this.buildingsFound = buildingsFound;
    }

    public void setVolume(float volume) {
        this.volume = volume;
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
        volume = 0;
    }
}
