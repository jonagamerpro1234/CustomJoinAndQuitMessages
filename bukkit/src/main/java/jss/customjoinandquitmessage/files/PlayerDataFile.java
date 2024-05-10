package jss.customjoinandquitmessage.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jss.customjoinandquitmessage.storage.PlayerData;

import java.io.*;

public class PlayerDataFile {

    private final File playersFolder;
    private final Gson gson;

    public PlayerDataFile(File pluginFolder) {
        this.playersFolder = new File(pluginFolder, "Players");
        if (!playersFolder.exists()) {
            playersFolder.mkdirs(); // Crea la carpeta si no existe
        }
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Método para cargar datos de un jugador desde un archivo JSON
    public PlayerData loadData(String playerName) {
        File playerFile = new File(playersFolder, playerName + ".json");
        try (FileReader reader = new FileReader(playerFile)) {
            return gson.fromJson(reader, PlayerData.class);
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, devuelve un nuevo objeto PlayerData
            return new PlayerData("default");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para guardar datos de un jugador en un archivo JSON
    public void saveData(String playerName, PlayerData data) {
        File playerFile = new File(playersFolder, playerName + ".json");
        try (FileWriter writer = new FileWriter(playerFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar si existe el archivo de datos del jugador
    public boolean playerDataFileExists(String playerName) {
        File playerFile = new File(playersFolder, playerName + ".json");
        return !playerFile.exists();
    }
}
