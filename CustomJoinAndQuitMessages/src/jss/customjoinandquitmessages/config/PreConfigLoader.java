package jss.customjoinandquitmessages.config;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventsUtils;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Utils;

public class PreConfigLoader {
	
	private CustomJoinAndQuitMessages plugin;
	private EventsUtils EventsUtils = new EventsUtils(plugin);
	
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
