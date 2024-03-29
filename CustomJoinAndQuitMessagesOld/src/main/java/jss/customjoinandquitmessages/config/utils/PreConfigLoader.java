package jss.customjoinandquitmessages.config.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.config.Lang;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class PreConfigLoader {

    private final CustomJoinAndQuitMessages plugin;

    public PreConfigLoader(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        FileConfiguration config = plugin.getConfigFile().getConfig();
        try {

            //Other
            Settings.settings_debug = config.getBoolean("Config.Debug");
            Settings.settings_update = Objects.equals(config.getString("Config.Update.Enabled"), "true");
            Settings.chatformat_type = config.getString("Config.Type");
            Settings.is_Group_Display = Objects.requireNonNull(config.getString("Config.Type")).equalsIgnoreCase("group");

            //Join
            Settings.join = Objects.equals(config.getString("Join.Enabled"), "true");
            Settings.join_message = config.getString("Join.Text");

            Settings.firstjoin = Objects.equals(config.getString("Join.First-Join.Enabled"), "true");
            Settings.join_message_first = config.getString("Join.First-Join.Text");

            Settings.join_actionbar = Objects.equals(config.getString("Join.ActionBar.Enabled"), "true");
            Settings.join_message_actionbar_text = config.getString("Join.ActionBar.Text");

            Settings.join_message_title_title = config.getString("Join.Title.Title");
            Settings.join_message_title_subtitle = config.getString("Join.Title.SubTitle");

            Settings.join_title_fadeIn = config.getInt("Join.Title.FadeIn");
            Settings.join_title_stay = config.getInt("Join.Title.Stay");
            Settings.join_title_fadeOut = config.getInt("Join.Title.FadeOut");

            Settings.join_sound = Objects.equals(config.getString("Join.Sound.Enabled"), "true");
            Settings.join_sound_name = config.getString("Join.Sound.Name");
            Settings.join_sound_pitch = Float.parseFloat(Objects.requireNonNull(config.getString("Join.Sound.Pitch")));
            Settings.join_sound_vol = config.getInt("Join.Sound.Volume");

            //Quit
            Settings.quit = Objects.equals(config.getString("Quit.Enabled"), "true");
            Settings.quit_message = config.getString("Quit.Text");

            //Welcome
            Settings.welcome = Objects.equals(config.getString("Welcome.Enabled"), "true");
            Settings.list_welcome = config.getStringList("Welcome.Text");

            //Hooks
            Settings.hook_discordSrv = Objects.equals(config.getString("Hooks.DiscordSRV.Enabled"), "true");
            Settings.hook_discordSrv_channelId = config.getString("Hooks.DiscordSRV.Channel-ID");

            Settings.hook_essentialsDiscord = Objects.equals(config.getString("Hooks.EssentialsDiscord.Enabled"), "true");
            Settings.hook_essentialsDiscord_channelId = config.getString("Hooks.EssentialsDiscord.Channel-ID");
            Settings.hook_essentialsDiscord_use_default_channel = config.getBoolean("Hooks.EssentialsDiscord.Use-Default-Channel");

            Settings.hook_luckperms = config.getBoolean("Hooks.LuckPerms.Enabled");
            Settings.hook_luckperms_autoUpdate_group = config.getBoolean("Hooks.LuckPerms.AutoUpdateGroup.Enabled");
            Settings.hook_luckperms_autoUpdate_group_tick = config.getLong("Hooks.LuckPerms.AutoUpdateGroup.Tick");

            Settings.hook_essentials = Objects.equals(config.getString("Hooks.Essentials.Enabled"), "true");
            Settings.hook_essentials_hidePlayerVanish = config.getBoolean("Hooks.Essentials.HidePlayerIsVanish");

        } catch (Exception e) {
            Logger.error("&cThere was an error loading the &b[PreConfigLoader]&7, please reload the plugin");
            e.printStackTrace();
        }
    }

    public boolean loadLangs() {
        Settings.settings_defaultLanguage = plugin.getConfig().getString("Config.Lang", "en_US");
        HashMap<String, Lang> availableLocales = new HashMap<>();
        FileLister fl = new FileLister();
        try {
            int index = 1;
            for (String code : fl.list()) {
                availableLocales.put(code, new Lang(plugin, code, index++));
            }
        } catch (IOException e1) {
            plugin.getLogger().severe("Could not add locales!");
        }
        if (!availableLocales.containsKey(Settings.settings_defaultLanguage)) {
            Logger.warning(Util.getPrefix(true) + "&eLoad File: " + Settings.settings_defaultLanguage + ".yml' not found in /lang/ folder. Using /lang/en_US.yml");
            Settings.settings_defaultLanguage = "en_US";
            availableLocales.put(Settings.settings_defaultLanguage, new Lang(plugin, Settings.settings_defaultLanguage, 0));
        }
        plugin.setAvailableLocales(availableLocales);
        return true;
    }
}
