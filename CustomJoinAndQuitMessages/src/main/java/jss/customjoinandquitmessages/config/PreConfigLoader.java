package jss.customjoinandquitmessages.config;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashMap;

public class PreConfigLoader {

    private final CustomJoinAndQuitMessages plugin;

    public PreConfigLoader(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        FileConfiguration config = plugin.getConfigFile().getConfig();
        try {

            //Other
            Settings.update = config.getString("Config.Update").equals("true");
            Settings.c_type = config.getString("Config.Type");
            Settings.is_Group_Display = config.getString("Config.Type").equalsIgnoreCase("group");

            //Join
            Settings.join = config.getString("Join.Enabled").equals("true");
            Settings.join_message = config.getString("Join.Text");
            Settings.join_type = config.getString("Join.Type");

            Settings.firstjoin = config.getString("Join.First-Join.Enabled").equals("true");
            Settings.join_message_first = config.getString("Join.First-Join.Text");

            Settings.join_actionbar = config.getString("Join.ActionBar.Enabled").equals("true");
            Settings.join_message_actionbar_text = config.getString("Join.ActionBar.Text");

            Settings.join_message_title_title = config.getString("Join.Title.Title");
            Settings.join_message_title_subtitle = config.getString("Join.Title.SubTitle");

            Settings.join_title_fadein = config.getInt("Join.Title.FadeIn");
            Settings.join_title_stay = config.getInt("Join.Title.Stay");
            Settings.join_title_fadeout = config.getInt("Join.Title.FadeOut");

            Settings.join_sound = config.getString("Join.Sound.Enabled").equals("true");
            Settings.join_sound_name = config.getString("Join.Sound.Name");
            Settings.join_sound_pitch = Float.valueOf(config.getString("Join.Sound.Pitch"));
            Settings.join_sound_vol = config.getInt("Join.Sound.Volume");

            //Quit
            Settings.quit = config.getString("Quit.Enabled").equals("true");
            Settings.quit_type = config.getString("Quit.Type");
            Settings.quit_message = config.getString("Quit.Text");

            //Welcome
            Settings.welcome = config.getString("Welcome.Enabled").equals("true");
            Settings.list_welcome = config.getStringList("Welcome.Text");

            //Hooks
            Settings.hook_discordsrv = config.getString("Hooks.DiscordSRV.Enabled").equals("true");
            Settings.hook_discordsrv_channelid = config.getString("Hooks.DiscordSRV.Channel-ID");

            Settings.hook_essentialsDiscord = config.getString("Hooks.EssentialsDiscord.Enabled").equals("true");
            Settings.hook_essentialsDiscord_channelid = config.getString("Hooks.EssentialsDiscord.Channel-ID");
            Settings.hook_essentialsDiscord_use_default_channel = config.getString("Hooks.EssentialsDiscord.Use-Default-Channel").equals("true");

            Settings.hook_luckperms = config.getString("Hooks.LuckPerms.Enabled").equals("true");
            Settings.hook_luckperms_autoUpdate_group = config.getString("Hooks.LuckPerms.AutoUpdateGroup.Enabled").equals("true");
            Settings.hook_luckperms_autoUpdate_group_tick = config.getLong("Hooks.LuckPerms.AutoUpdateGroup.Tick");

            Settings.hook_essentials = config.getString("Hooks.Essentials.Enabled").equals("true");
            Settings.hook_essentials_hideplayervanish = config.getString("Hooks.Essentials.HidePlayerIsVanish").equals("true");

        } catch (Exception e) {
            Logger.error("&cThere was an error loading the &b[PreConfigLoader]&7, please reload the plugin");
            e.printStackTrace();
        }
    }

    public boolean loadLangs() {
        Settings.defaultLanguage = plugin.getConfig().getString("Config.Lang", "en-US");
        HashMap<String, Lang> availableLocales = new HashMap<String, Lang>();
        FileListener fl = new FileListener();
        try {
            int index = 1;
            for (String code : fl.list()) {
                availableLocales.put(code, new Lang(plugin, code, index++));
            }
        } catch (IOException e1) {
            plugin.getLogger().severe("Could not add locales!");
        }
        if (!availableLocales.containsKey(Settings.defaultLanguage)) {
            Logger.warning(Util.getPrefix() + "&eLoad File: " + Settings.defaultLanguage + ".yml' not found in /locale folder. Using /locale/en-US.yml");
            Settings.defaultLanguage = "en-US";
            availableLocales.put(Settings.defaultLanguage, new Lang(plugin, Settings.defaultLanguage, 0));
        }
        plugin.setAvailableLocales(availableLocales);
        return true;
    }
}
