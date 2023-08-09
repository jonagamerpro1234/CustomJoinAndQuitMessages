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
        Settings.chatformat_type = config.getString("ChatFormat.Type");
        Settings.chatformat_showChatInConsole = config.getBoolean("ChatFormat.ShowChatInConsole");

        Settings.chatformat_join_enabled = config.getBoolean("ChatFormat.Join.Enabled");
        Settings.chatformat_join_message = config.getString("ChatFormat.Join.Format");
        Settings.chatformat_join_delay = config.getInt("ChatFormat.Join.");
        Settings.chatformat_join_titleSettings_enabled = config.getBoolean("ChatFormat.Join.");
        Settings.chatformat_join_titleSettings_title = config.getString("ChatFormat.Join.");
        Settings.chatformat_join_titleSettings_subtitle = config.getString("ChatFormat.Join.");
        Settings.chatformat_join_titleSettings_fadeIn = config.getInt("ChatFormat.Join.");
        Settings.chatformat_join_titleSettings_stay = config.getInt("ChatFormat.Join.");
        Settings.chatformat_join_titleSettings_fadeOut = config.getInt("ChatFormat.Join.");
        Settings.chatformat_join_actionbar_enabled = config.getBoolean("ChatFormat.Join.");
        Settings.chatformat_join_actionbar_message = config.getString("ChatFormat.Join.");
        Settings.chatformat_join_actionbar_delay = config.getInt("ChatFormat.Join.");
        Settings.chatformat_join_sound_enabled = config.getBoolean("ChatFormat.Join.");
        Settings.chatformat_join_sound_sendToAll  = config.getBoolean("ChatFormat.Join.");
        Settings.chatformat_join_sound_name = config.getString("ChatFormat.Join.");
        Settings.chatformat_join_sound_volume = config.getInt("ChatFormat.Join.");
        Settings.chatformat_join_sound_pitch = config.getInt("ChatFormat.Join.");

        Settings.chatformat_firstJoin_enabled = config.getBoolean("ChatFormat.First-Join.Enabled");
        Settings.chatformat_firstJoin_message = config.getString("ChatFormat.First-Join.Format");
        Settings.chatformat_firstJoin_delay = config.getInt("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_titleSettings_enabled = config.getBoolean("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_titleSettings_title = config.getString("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_titleSettings_subtitle = config.getString("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_titleSettings_fadeIn = config.getInt("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_titleSettings_stay = config.getInt("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_titleSettings_fadeOut = config.getInt("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_actionbar_enabled = config.getBoolean("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_actionbar_message = config.getString("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_actionbar_delay = config.getInt("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_sound_enabled = config.getBoolean("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_sound_sendToAll  = config.getBoolean("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_sound_name = config.getString("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_sound_volume = config.getInt("ChatFormat.First-Join.");
        Settings.chatformat_firstJoin_sound_pitch = config.getInt("ChatFormat.First-Join.");

        Settings.chatformat_quit_enabled = config.getBoolean("ChatFormat.Quit.Enabled");
        Settings.chatformat_quit_message = config.getString("ChatFormat.Quit.Format");
        Settings.chatformat_quit_delay = config.getInt("ChatFormat.Quit.");
        Settings.chatformat_quit_titleSettings_enabled = config.getBoolean("ChatFormat.Quit.");
        Settings.chatformat_quit_titleSettings_title = config.getString("ChatFormat.Quit.");
        Settings.chatformat_quit_titleSettings_subtitle = config.getString("ChatFormat.Quit.");
        Settings.chatformat_quit_titleSettings_fadeIn = config.getInt("ChatFormat.Quit.");
        Settings.chatformat_quit_titleSettings_stay = config.getInt("ChatFormat.Quit.");
        Settings.chatformat_quit_titleSettings_fadeOut = config.getInt("ChatFormat.Quit.");
        Settings.chatformat_quit_actionbar_enabled = config.getBoolean("ChatFormat.Quit.");
        Settings.chatformat_quit_actionbar_message = config.getString("ChatFormat.Quit.");
        Settings.chatformat_quit_actionbar_delay = config.getInt("ChatFormat.Quit.");
        Settings.chatformat_quit_sound_enabled = config.getBoolean("ChatFormat.Quit.");
        Settings.chatformat_quit_sound_sendToAll  = config.getBoolean("ChatFormat.Quit.");
        Settings.chatformat_quit_sound_name = config.getString("ChatFormat.Quit.");
        Settings.chatformat_quit_sound_volume = config.getInt("ChatFormat.Quit.");
        Settings.chatformat_quit_sound_pitch = config.getInt("ChatFormat.Quit.");

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
