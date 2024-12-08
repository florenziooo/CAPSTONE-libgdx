package io.mygame.datahandler;

import io.mygame.common.GameManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The GameDataHandler class manages loading and saving game data (such as player information, NPCs, and volume)
 * from and to a JSON file. It ensures the game data directory exists and handles exceptions related to file I/O.
 * The class uses a singleton GameManager instance to retrieve and store game data.
 */
public class GameDataHandler {
    /************ GAME MANAGER ************/
    private static final GameManager gameManagerInstance = GameManager.getInstance();

    /************ GAME DATA DIRECTORY ************/
    public static final String dataDirectory = "../GameData";

    /************ DATA FILE PATH ************/
    private static final String dataPath = dataDirectory + "/data.json";

    /************ JSON SERVICE ************/
    private static final JSONService jsonService = new JSONService();

    /**
     * Private constructor to prevent instantiation of the GameDataHandler class.
     */
    private GameDataHandler() {}

    /**
     * Loads game data from a JSON file and updates the GameManager instance.
     * The data includes player name, NPC interactions, building progress, volume settings, player position,
     * areas found, and NPCs interacted with.
     */
    public static void loadGameData() {
        try (FileReader fileReader = new FileReader(dataPath)) {
            // Deserialize the game data from the file
            GameManager gameManager = jsonService.deserialize(fileReader, GameManager.class);

            // Update the GameManager instance with loaded data
            gameManagerInstance.setPlayerName(gameManager.getPlayerName());
            gameManagerInstance.setNpcFound(gameManager.getNpcFound());
            gameManagerInstance.setBuildingFound(gameManager.getBuildingsFound());
            gameManagerInstance.setVolume(gameManager.getVolume());
            gameManagerInstance.setPlayerPosition(gameManager.getPlayerPosition());
            gameManagerInstance.setAreasFound(gameManager.getAreasFound());
            gameManagerInstance.setNpcsFound(gameManager.getNpcInteracted());
        } catch (IOException e) {
            System.err.println("Could not read data file: " + dataPath);
        }
    }

    /**
     * Saves the current game data to a JSON file.
     * If the game data directory does not exist, it is created.
     */
    public static void saveGameData() {
        // Ensure the game data directory exists
        File directory = new File(dataDirectory);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                System.err.println("Failed to create directory: " + dataDirectory);
                return;
            }
        }

        // Serialize the current game state and save to the data file
        try (FileWriter fileWriter = new FileWriter(dataPath)) {
            jsonService.serializedObject(gameManagerInstance, fileWriter);
        } catch (IOException e) {
            System.err.println("Could not create data file: " + dataPath);
        }
    }
}
