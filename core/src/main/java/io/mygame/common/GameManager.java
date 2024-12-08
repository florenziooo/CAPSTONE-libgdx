package io.mygame.common;

import io.mygame.datahandler.Serializable;
import java.util.HashMap;

/**
 * The GameManager class is responsible for managing game state, player data, and game settings.
 * It holds information about the player's progress, including discovered areas, NPC interactions,
 * and sound settings. The GameManager follows a singleton pattern.
 */
public class GameManager implements Serializable {
    /************ SINGLETON INSTANCE ************/
    private static GameManager instance;

    /************ PLAYER INFORMATION ************/
    private String playerName;
    private int npcFound;
    private int buildingsFound;

    /************ GAME SETTINGS ************/
    private float volume;
    private int[] playerPosition;

    /************ GAME PROGRESS ************/
    private HashMap<String, Boolean> areasFound;
    private HashMap<String, Boolean> npcInteracted;

    /************ SOUND MANAGEMENT ************/
    private transient SoundManager soundManager;

    /**
     * CONSTRUCTOR that calls resetDefaultValues method.
     */
    private GameManager() {
        resetDefaultValues();
    }

    /**
     * Sets the player's name.
     * @param playerName The player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Sets the number of NPCs the player has found.
     * @param npcFound The number of NPCs found.
     */
    public void setNpcFound(int npcFound) {
        this.npcFound = npcFound;
    }

    /**
     * Sets the number of buildings the player has found.
     * @param buildingsFound The number of buildings found.
     */
    public void setBuildingFound(int buildingsFound) {
        this.buildingsFound = buildingsFound;
    }

    /**
     * Sets the SoundManager instance for managing sound effects and volume.
     * @param soundManager The SoundManager instance.
     */
    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    /**
     * Sets the global volume and adjusts the SoundManager volume if available.
     * @param volume The volume level to set (0.0 to 1.0).
     */
    public void setVolume(float volume) {
        this.volume = volume;
        if (soundManager != null) {
            soundManager.setGlobalVolume(this.volume);
        }
    }

    /**
     * Sets the player's position using an array of coordinates [x, y].
     * @param playerPosition An array representing the player's position.
     */
    public void setPlayerPosition(int[] playerPosition) {
        this.playerPosition = playerPosition;
    }

    /**
     * Sets the player's position using separate x and y values.
     * @param x The x-coordinate of the player's position.
     * @param y The y-coordinate of the player's position.
     */
    public void setPlayerPosition(int x, int y) {
        this.playerPosition = new int[]{x, y};
    }

    /**
     * Sets the discovered areas HashMap.
     * @param areasFound The map of areas and their discovered status.
     */
    public void setAreasFound(HashMap<String, Boolean> areasFound) {
        this.areasFound = areasFound;
    }

    /**
     * Marks an area as discovered.
     * @param type The area type (e.g., building name) to mark as discovered.
     */
    public void setAreaFound(String type) {
        if(!areasFound.get(type)) buildingsFound++;
        areasFound.put(type, true);
    }

    /**
     * Marks an NPC as interacted with.
     * @param key The NPC key (e.g., "Janitor") to mark as interacted.
     */
    public void setNpcInteracted(String key) {
        if(!npcInteracted.get(key)) npcFound++;
        npcInteracted.put(key, true);
    }

    /**
     * Sets the HashMap for NPC interactions.
     * @param npcInteracted The map of NPCs and their interaction status.
     */
    public void setNpcsFound(HashMap<String, Boolean> npcInteracted) {
        this.npcInteracted = npcInteracted;
    }

    /**
     * Gets the singleton instance of the GameManager.
     * @return The GameManager instance.
     */
    public static GameManager getInstance() {
        if(instance == null) instance = new GameManager();
        return instance;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the number of NPCs the player has interacted with.
     * @return The number of NPCs found.
     */
    public int getNpcFound() {
        return npcFound;
    }

    /**
     * Gets the number of buildings the player has discovered.
     * @return The number of buildings found.
     */
    public int getBuildingsFound() {
        return buildingsFound;
    }

    /**
     * Gets the current volume level.
     * @return The volume level (0.0 to 1.0).
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Gets the SoundManager instance.
     * @return The SoundManager instance.
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Gets the player's position as an array [x, y].
     * @return An array with the player's position.
     */
    public int[] getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Gets the x-coordinate of the player's position.
     * @return The x-coordinate of the player's position.
     */
    public int getXPosition() {
        return playerPosition[0];
    }

    /**
     * Gets the y-coordinate of the player's position.
     * @return The y-coordinate of the player's position.
     */
    public int getYPosition() {
        return playerPosition[1];
    }

    /**
     * Gets whether a specific area has been discovered.
     * @param key The area key (e.g., building name).
     * @return True if the area is discovered, false otherwise.
     */
    public boolean getAreaFound(String key) {
        Boolean value = areasFound.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Key: " + key + " not found in HashMap as part of the discoverable areas");
        }
        return value;
    }

    /**
     * Gets the HashMap of NPC interactions.
     * @return The map of NPCs and their interaction status.
     */
    public HashMap<String, Boolean> getNpcInteracted() {
        return npcInteracted;
    }

    /**
     * Gets the HashMap of discovered areas.
     * @return The map of areas and their discovered status.
     */
    public HashMap<String, Boolean> getAreasFound() {
        return areasFound;
    }

    /**
     * Resets the GameManager to its default values.
     */
    public void resetDefaultValues() {
        playerName = null;
        npcFound = 0;
        buildingsFound = 0;
        volume = 1.0f;

        playerPosition = new int[]{1744, 175};

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
