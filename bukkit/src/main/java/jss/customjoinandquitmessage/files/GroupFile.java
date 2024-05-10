package jss.customjoinandquitmessage.files;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@SuppressWarnings("All")
public class GroupFile {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();
    private File file;
    private FileConfiguration config;

    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }
        return this.config;
    }

    public void saveConfig() {
        try {
            this.config.save(this.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        if (this.config == null) {
            this.file = new File(plugin.getDataFolder(), "groups.yml");
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        Reader defaultConfigStream;
        try {
            defaultConfigStream = new InputStreamReader(Objects.requireNonNull(plugin.getResource("groups.yml")), StandardCharsets.UTF_8);
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
            config.setDefaults(defaultConfig);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (this.file == null) {
            this.file = new File(plugin.getDataFolder(), "groups.yml");
        }
        if (!this.file.exists()) {
            plugin.saveResource("groups.yml", false);
        }
    }

}
