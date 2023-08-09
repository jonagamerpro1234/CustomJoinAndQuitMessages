package jss.customjoinandquitmessage.files;

import com.google.gson.Gson;
import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.storage.PlayerData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerDataManager {

    private static final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    private static final String DATA_FOLDER = "Players";
    private static final Gson gson = new Gson();

    public static void createPlayerFile(@NotNull Player player) {
        String fileName = player.getName() + ".json";
        File file = new File(plugin.getDataFolder() + File.separator + DATA_FOLDER, fileName);

        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static @Nullable PlayerData loadPlayerData(@NotNull Player player) {
        String fileName = player.getName() + ".json";
        File file = new File(plugin.getDataFolder() + File.separator + DATA_FOLDER, fileName);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, PlayerData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void savePlayerData(@NotNull Player player, PlayerData playerData) {
        String fileName = player.getName() + ".json";
        File file = new File(plugin.getDataFolder() + File.separator + DATA_FOLDER, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(playerData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
