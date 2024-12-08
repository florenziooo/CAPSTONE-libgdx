package io.mygame.datahandler;

import io.mygame.common.GameManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameDataHandler {
    private static final GameManager gameManagerInstance = GameManager.getInstance();
    public static final String dataDirectory = "../GameData";
    private static final String dataPath = dataDirectory + "/data.json";
    private static final JSONService jsonService = new JSONService();

    private GameDataHandler() {}

    public static void loadGameData() {
        try(FileReader fileReader = new FileReader(dataPath)) {
            GameManager gameManager = jsonService.deserialize(fileReader, GameManager.class);

            gameManagerInstance.setPlayerName(gameManager.getPlayerName());
            gameManagerInstance.setNpcFound(gameManager.getNpcFound());
            gameManagerInstance.setBuildingFound(gameManager.getBuildingsFound());
            gameManagerInstance.setVolume(gameManager.getVolume());
            gameManagerInstance.setPlayerPosition(gameManager.getPlayerPosition());
            gameManagerInstance.setAreasFound(gameManager.getAreasFound());
            gameManagerInstance.setNpcsFound(gameManager.getNpcInteracted());

        } catch(IOException e) {
            System.err.println("Could not read data file: " + dataPath);
        }
    }

    public static void saveGameData() {
        File directory = new File(dataDirectory);
        if(!directory.exists()) {
            if (!directory.mkdir()) {
                System.err.println("Failed to create directory: " + dataDirectory);
                return;
            }
        }

        try(FileWriter fileWriter = new FileWriter(dataPath)) {
            jsonService.serializedObject(gameManagerInstance, fileWriter);
        } catch(IOException e) {
            System.err.println("Could not create data file: " + dataPath);
        }
    }
}
