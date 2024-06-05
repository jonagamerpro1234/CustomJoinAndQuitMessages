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

    public void loadConfigs(){
        FileConfiguration config = plugin.getConfig();

        //General Settings
        Settings.config_Lang =  config.getString("Settings.Lang");
        Settings.config_Debug = config.getBoolean("Settings.Debug");
        Settings.chatformat_type = config.getString("ChatFormat.Type");
        Settings.chatformat_showChatInConsole = config.getBoolean("ChatFormat.ShowChatInConsole");

        //DataBase
        Settings.db_enabled = config.getBoolean("Database.Enabled");
        Settings.db_host = config.getString("Database.Host");
        Settings.db_port = config.getInt("Database.Port");
        Settings.db_user = config.getString("Database.User");
        Settings.db_password = config.getString("Database.Password", "");
        Settings.db_database_name = config.getString("Database.Database");

        //Luckperms Hook
        Settings.luckperms_enabled = config.getBoolean("Hooks.LuckPerms.Enabled");
        Settings.luckperms_autoUpdateGroup_enabled = config.getBoolean("Hooks.LuckPerms.AutoUpdateGroup.Enabled");
        Settings.luckperms_autoUpdateGroup_delay = config.getInt("Hooks.LuckPerms.AutoUpdateGroup.Delay");
        Settings.luckperms_autoUpdateGroup_tick = config.getInt("Hooks.LuckPerms.AutoUpdateGroup.Tick");

        //ChatFormat
        Settings.chatformat_join_enabled = config.getBoolean("ChatFormat.Join.Enabled");
        Settings.chatformat_join_message = config.getString("ChatFormat.Join.Format");
        Settings.chatformat_join_delay = config.getInt("ChatFormat.Join.JoinMessageDelay");
        Settings.chatformat_join_titleSettings_enabled = config.getBoolean("ChatFormat.Join.Title-Settings.Enabled");
        Settings.chatformat_join_titleSettings_title = config.getString("ChatFormat.Join.Title-Settings.Title");
        Settings.chatformat_join_titleSettings_subtitle = config.getString("ChatFormat.Join.Title-Settings.SubTitle");
        Settings.chatformat_join_titleSettings_fadeIn = config.getInt("ChatFormat.Join.Title-Settings.FadeIn");
        Settings.chatformat_join_titleSettings_stay = config.getInt("ChatFormat.Join.Title-Settings.Stay");
        Settings.chatformat_join_titleSettings_fadeOut = config.getInt("ChatFormat.Join.Title-Settings.FadeOut");
        Settings.chatformat_join_actionbar_enabled = config.getBoolean("ChatFormat.Join.Actionbar.Enabled");
        Settings.chatformat_join_actionbar_message = config.getString("ChatFormat.Join.Actionbar.Format");
        Settings.chatformat_join_actionbar_delay = config.getInt("ChatFormat.Join.Actionbar.Delay");
        Settings.chatformat_join_sound_enabled = config.getBoolean("ChatFormat.Join.Sound.Enabled");
        Settings.chatformat_join_sound_sendToAll  = config.getBoolean("ChatFormat.Join.Sound.SendToAll");
        Settings.chatformat_join_sound_name = config.getString("ChatFormat.Join.Sound.Name");
        Settings.chatformat_join_sound_volume = config.getInt("ChatFormat.Join.Sound.Volume");
        Settings.chatformat_join_sound_pitch = config.getInt("ChatFormat.Join.Sound.Pitch");

        Settings.chatformat_firstJoin_enabled = config.getBoolean("ChatFormat.First-Join.Enabled");
        Settings.chatformat_firstJoin_message = config.getString("ChatFormat.First-Join.Format");
        Settings.chatformat_firstJoin_delay = config.getInt("ChatFormat.First-Join.JoinMessageDelay");
        Settings.chatformat_firstJoin_titleSettings_enabled = config.getBoolean("ChatFormat.First-Join.Title-Settings.Enabled");
        Settings.chatformat_firstJoin_titleSettings_title = config.getString("ChatFormat.First-Join.Title-Settings.Title");
        Settings.chatformat_firstJoin_titleSettings_subtitle = config.getString("ChatFormat.First-Join.Title-Settings.SubTitle");
        Settings.chatformat_firstJoin_titleSettings_fadeIn = config.getInt("ChatFormat.First-Join.Title-Settings.FadeIn");
        Settings.chatformat_firstJoin_titleSettings_stay = config.getInt("ChatFormat.First-Join.Title-Settings.Stay");
        Settings.chatformat_firstJoin_titleSettings_fadeOut = config.getInt("ChatFormat.First-Join.Title-Settings.FadeOut");
        Settings.chatformat_firstJoin_actionbar_enabled = config.getBoolean("ChatFormat.First-Join.Actionbar.Enabled");
        Settings.chatformat_firstJoin_actionbar_message = config.getString("ChatFormat.First-Join.Actionbar.Format");
        Settings.chatformat_firstJoin_actionbar_delay = config.getInt("ChatFormat.First-Join.Actionbar.Delay");
        Settings.chatformat_firstJoin_sound_enabled = config.getBoolean("ChatFormat.First-Join.Sound.Enabled");
        Settings.chatformat_firstJoin_sound_sendToAll  = config.getBoolean("ChatFormat.First-Join.Sound.SendToAll");
        Settings.chatformat_firstJoin_sound_name = config.getString("ChatFormat.First-Join.Sound.Name");
        Settings.chatformat_firstJoin_sound_volume = config.getInt("ChatFormat.First-Join.Sound.Volume");
        Settings.chatformat_firstJoin_sound_pitch = config.getInt("ChatFormat.First-Join.Sound.Pitch");

        Settings.chatformat_quit_enabled = config.getBoolean("ChatFormat.Quit.Enabled");
        Settings.chatformat_quit_message = config.getString("ChatFormat.Quit.Format");
        Settings.chatformat_quit_delay = config.getInt("ChatFormat.Quit.JoinMessageDelay");
        Settings.chatformat_quit_titleSettings_enabled = config.getBoolean("ChatFormat.Quit.Title-Settings.Enabled");
        Settings.chatformat_quit_titleSettings_title = config.getString("ChatFormat.Quit.Title-Settings.Title");
        Settings.chatformat_quit_titleSettings_subtitle = config.getString("ChatFormat.Quit.Title-Settings.SubTitle");
        Settings.chatformat_quit_titleSettings_fadeIn = config.getInt("ChatFormat.Quit.Title-Settings.FadeIn");
        Settings.chatformat_quit_titleSettings_stay = config.getInt("ChatFormat.Quit.Title-Settings.Stay");
        Settings.chatformat_quit_titleSettings_fadeOut = config.getInt("ChatFormat.Quit.Title-Settings.FadeOut");
        Settings.chatformat_quit_actionbar_enabled = config.getBoolean("ChatFormat.Quit.Actionbar.Enabled");
        Settings.chatformat_quit_actionbar_message = config.getString("ChatFormat.Quit.Actionbar.Format");
        Settings.chatformat_quit_actionbar_delay = config.getInt("ChatFormat.Quit.Actionbar.Delay");
        Settings.chatformat_quit_sound_enabled = config.getBoolean("ChatFormat.Quit.Sound.Enabled");
        Settings.chatformat_quit_sound_sendToAll  = config.getBoolean("ChatFormat.Quit.Sound.SendToAll");
        Settings.chatformat_quit_sound_name = config.getString("ChatFormat.Quit.Sound.Name");
        Settings.chatformat_quit_sound_volume = config.getInt("ChatFormat.Quit.Sound.Volume");
        Settings.chatformat_quit_sound_pitch = config.getInt("ChatFormat.Quit.Sound.Pitch");

        Settings.welcome_enabled = config.getBoolean("Welcome.Enabled");
        Settings.welcome_message = config.getStringList("Welcome.Text");
        Settings.welcome_delay = config.getInt("Welcome.Delay");
        Settings.welcome_UseWelcomePerGroup = config.getBoolean("Welcome.UseWelcomePerGroup");

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
            //[debug] Settings.config_Lang = "en_US";
            availableLangs.put(Settings.config_Lang, new LangFile(plugin, Settings.config_Lang,0));
        }
        return true;
    }

}
