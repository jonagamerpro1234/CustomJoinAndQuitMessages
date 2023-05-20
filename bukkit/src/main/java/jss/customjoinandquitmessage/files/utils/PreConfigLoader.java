package jss.customjoinandquitmessage.files.utils;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.LangFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashMap;

public class PreConfigLoader {

    private final CustomJoinAndQuitMessage plugin;

    public PreConfigLoader(CustomJoinAndQuitMessage plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    public void loadConfigs(){
        FileConfiguration config = plugin.getConfig();
        Settings.config_Lang =  config.getString("Settings.Lang");
        Settings.config_Debug = config.getBoolean("Settings.Debug");

    }

    public boolean loadLangs(){
        HashMap<String, LangFile> availableLangs = new HashMap<>();
        FileList fileList = new FileList();
        int index = 1;

        try {
            for(String code : fileList.list()){
                availableLangs.put(code, new LangFile(plugin, code, index++));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!availableLangs.containsKey(Settings.config_Lang)){
            Settings.config_Lang = "en_US";
            availableLangs.put(Settings.config_Lang, new LangFile(plugin, Settings.config_Lang,0));
        }
        return true;
    }

}
