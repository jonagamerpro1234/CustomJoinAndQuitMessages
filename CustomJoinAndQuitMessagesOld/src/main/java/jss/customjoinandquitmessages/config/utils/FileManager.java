package jss.customjoinandquitmessages.config.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

public class FileManager {

    private final CustomJoinAndQuitMessages plugin;

    public FileManager(CustomJoinAndQuitMessages plugin) {
        super();
        this.plugin = plugin;
    }

    public void createVoidFolder(String name) {
        File folder = new File(getDataFolder(), name);
        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createFolderAndFile(String nameFolder, String nameFile) {
        File folder = new File(getDataFolder(), nameFolder);
        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File file = new File(folder, nameFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Deprecated
    public JavaPlugin getJavaPlugin() {
        return plugin;
    }

    public void saveResources(String filename, boolean replace) {
        plugin.saveResource(filename, replace);
    }

    public InputStream getResources(String filename) {
        return plugin.getResource(filename);
    }

    public File getDataFolder() {
        return plugin.getDataFolder();
    }

}
