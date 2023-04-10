package jss.customjoinandquitmessages.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerJsonStorage {

    private final Gson gson;
    private File file;

    private final String basePath;

    public PlayerJsonStorage(@NotNull CustomJoinAndQuitMessages plugin) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        basePath = plugin.getDataFolder() + File.separator + "Players";
    }

    public void savePlayerData(@NotNull PlayerData playerData) {
        file = new File(basePath, playerData.getName() + ".json");
        if(file.exists()){
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(playerData, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            try{
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                gson.toJson(playerData,writer);
                writer.flush();
                writer.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public PlayerData loadPlayerData(String playerName) {
        file = new File(basePath, playerName + ".json");
        if(file.exists()){
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, PlayerData.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            System.out.println("archivo no existe, se procede a crearlo");
            try {
                file.createNewFile();
                return new PlayerData(playerName);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void removePlayerData(String playerName) {
        file = new File(basePath, playerName + ".json");
        if (file.exists()){
            file.delete();
        }
    }

}
