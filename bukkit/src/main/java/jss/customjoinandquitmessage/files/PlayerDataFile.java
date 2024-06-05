package jss.customjoinandquitmessage.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jss.customjoinandquitmessage.storage.CacheManager;
import jss.customjoinandquitmessage.storage.PlayerData;

import java.io.*;

public class PlayerDataFile {

    private final File playersFolder;
    private final Gson gson;

    public PlayerDataFile(File pluginFolder) {
        this.playersFolder = new File(pluginFolder, "Players");
        if (!playersFolder.exists()) {
            playersFolder.mkdirs(); // Create the folder if it does not exist
        }
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Method to load player data from a JSON file
    public PlayerData loadData(String playerName) {
        File playerFile = new File(playersFolder, playerName + ".json");
        try (FileReader reader = new FileReader(playerFile)) {
            return gson.fromJson(reader, PlayerData.class);
        } catch (FileNotFoundException e) {
            // If the file does not exist, returns a new PlayerData object
            return new PlayerData("default");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to save player data to a JSON file
    public void saveData(String playerName, PlayerData data) {
        File playerFile = new File(playersFolder, playerName + ".json");
        try (FileWriter writer = new FileWriter(playerFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if the player data file exists
    public boolean playerDataFileExists(String playerName) {
        File playerFile = new File(playersFolder, playerName + ".json");
        return !playerFile.exists();
    }

}
