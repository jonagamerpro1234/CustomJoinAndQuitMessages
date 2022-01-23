package jss.customjoinandquitmessages.config;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Utils;

public class PreConfigLoader {
	
	private CustomJoinAndQuitMessages plugin;
	private EventUtils EventsUtils = new EventUtils(plugin);
	
	public PreConfigLoader(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public void loadConfig() {
		FileConfiguration config = plugin.getConfigFile().getConfig();
		try {
			Settings.boolean_join = config.getString("Join.Enabled").equals("true");
			Settings.boolean_quit = config.getString("Quit.Enabled").equals("true");
			Settings.boolean_welcome = config.getString("Welcome.Enabled").equals("true");
			Settings.boolean_update = config.getString("Config.Update").equals("true");
			Settings.c_type = config.getString("Config.Type");
			Settings.message_join = config.getString("Join.Text");
			Settings.join_type = config.getString("Join.Type");
			Settings.boolean_firstjoin = config.getString("Join.First-Join.Enabled").equals("true");
			Settings.message_first_join = config.getString("Join.First-Join.Text");
			Settings.quit_type = config.getString("Quit.Type");
			Settings.message_quit = config.getString("Quit.Text");
			Settings.list_welcome = config.getStringList("Welcome.Text");
        	Settings.hook_discordsrv = config.getString("Hooks.DiscordSRV.Enabled").equals("true");
        	Settings.hook_discordsrv_channelid = config.getString("Hooks.DiscordSRV.Channel-ID");
        	Settings.hook_essentialsDiscord = config.getString("Hooks.EssentialsDiscord.Enabled").equals("true");
        	Settings.hook_essentialsDiscord_channelid = config.getString("Hooks.EssentialsDiscord.Channel-ID");
        	Settings.hook_essentialsDiscord_use_default_channel = config.getString("Hooks.EssentialsDiscord.Use-Default-Channel").equals("true");
        	Settings.hook_vault = config.getString("Hooks.Vault.Enabled").equals("true");
        	Settings.hook_vault_use_group = config.getString("Hooks.Vault.Use-Vault-In-Groups").equals("true");
        	Settings.hook_luckperms = config.getString("Hooks.LuckPerms.Enabled").equals("true");
        	Settings.hook_luckperms_use_group = config.getString("Hooks.LuckPerms.Use-Luckperms-In-Groups").equals("true");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean loadLangs() {
	    Settings.defaultLanguage = plugin.getConfig().getString("Config.Lang", "en-US");
	    HashMap<String,Lang> availableLocales = new HashMap<String,Lang>();
	    FileListener fl = new FileListener(plugin);
	    try {
	        int index = 1;
	        for (String code: fl.list()) {
	            availableLocales.put(code, new Lang(plugin, code, index++));
	        }
	    } catch (IOException e1) {
	        plugin.getLogger().severe("Could not add locales!");
	    }
	    if (!availableLocales.containsKey(Settings.defaultLanguage)) {
	    	Utils.sendColorMessage(EventsUtils.getConsoleSender(), Utils.getPrefix() + "&eLoad File: " + Settings.defaultLanguage + ".yml' not found in /locale folder. Using /locale/en-US.yml");	
	        Settings.defaultLanguage = "en-US";
	        availableLocales.put(Settings.defaultLanguage, new Lang(plugin, Settings.defaultLanguage, 0));
	    }
	    plugin.setAvailableLocales(availableLocales);
	    return true;
	}
}
