package jss.customjoinandquitmessages;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import jss.customjoinandquitmessages.commands.CustomJoinAndQuitCmd;
import jss.customjoinandquitmessages.events.JoinListener;
import jss.customjoinandquitmessages.events.SoundsListener;
import jss.customjoinandquitmessages.hook.HooksManager;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Lang;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Logger.Level;
import jss.customjoinandquitmessages.utils.PluginConfig;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.UpdateSettings;
import jss.customjoinandquitmessages.utils.Utils;

public class CustomJoinAndQuitMessages extends JavaPlugin{

	PluginDescriptionFile jss = getDescription();
	public String name = this.jss.getName();
	public String version = this.jss.getVersion();
	private static CustomJoinAndQuitMessages plugin;
	public boolean placeholders = false;
	public boolean vault = false;
	public Metrics metrics;
	public String latestversion;
    public boolean useLegacyversions = false;
    public String nmsversion;
	private Map<String,Lang> availableLangs = new HashMap<>();
	private EventUtils eventUtils = new EventUtils(this);
	private ConfigFile configFile = new ConfigFile(this, "config.yml");
	private Logger logger = new Logger(this);
	private HooksManager hooksManager = new HooksManager(this);
	
	public void onEnable() {
		Utils.setEnabled(version);
		configFile.saveDefaultConfig();
		configFile.create();
		if(!PluginConfig.loadConfig(this)) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&b"+ name +"&e]&c error load lang and config file");
			return;
		}		
		metrics = new Metrics(this);
		plugin = this;
        nmsversion = Bukkit.getServer().getClass().getPackage().getName();
        nmsversion = nmsversion.substring(nmsversion.lastIndexOf(".") + 1);
        if (nmsversion.equalsIgnoreCase("v1_8_R1") || nmsversion.equalsIgnoreCase("v1_7_")) { 
        	useLegacyversions = true;
        	if(useLegacyversions == true) {
        		Utils.sendColorMessage(eventUtils.getConsoleSender(), " Test 1.7_? | 1.8_R1");
        	}
        }
        if (nmsversion.equalsIgnoreCase("v1_8_R3")) { 
        	useLegacyversions = true;
        	if(useLegacyversions == true) {
        		Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&7Use " + nmsversion + " &cdisabled &7method &b1.16");
        	}
        }else if(nmsversion.equalsIgnoreCase("v1_16_R1") || nmsversion.equalsIgnoreCase("v1_16_R2") || nmsversion.equalsIgnoreCase("v1_16_R3")){
        	Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + " " + "&7Use " + nmsversion + " &aenabled &7method &b1.16");
        }
		setupCommands();
		setupEvents();
		hooksManager.load();
		
		new UpdateChecker(this, UpdateSettings.ID).getUpdateVersion(version -> {
			if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
				logger.Log(Level.SUCCESS, "&a" + this.name + " is up to date!");
			}else {
                logger.Log(Level.OUTLINE, "&5<||" + Utils.setLine("&5"));
                logger.Log(Level.WARNING, "&5<||" + "&b" + this.name + " is outdated!");
                logger.Log(Level.WARNING, "&5<||" + "&bNewest version: &a" + version);
                logger.Log(Level.WARNING, "&5<||" + "&bYour version: &d" + UpdateSettings.VERSION);
                logger.Log(Level.WARNING, "&5<||" + "&bUpdate Here on Spigot: &e" + UpdateSettings.URL_PlUGIN[0]);
                logger.Log(Level.OUTLINE, "&5<||" + Utils.setLine("&5"));
			}
		});
	}
	
	public void onDisable() {
		Utils.setDisabled(version);
		metrics = null;
		placeholders = false;
		vault = false;
	}
	
	public void setupCommands() {
		new CustomJoinAndQuitCmd(this);
	}
	
	public void setupEvents() {
		new JoinListener(this);
		new SoundsListener(this);
	}
	
	public static CustomJoinAndQuitMessages getPlugin() {
		return plugin;
	}
	
	
	public Lang Locale() {
		return availableLangs.get(Settings.defaultLanguage);
	}   
	
	public Map<String, Lang> getAvailableLocales() {
		return availableLangs;
	}
	    
	public void setAvailableLocales(HashMap<String, Lang> availableLangs) {
		this.availableLangs = availableLangs;
	}
	
	public boolean isUseLegacyversions() {
		return useLegacyversions;
	}

	public String getNmsversion() {
		return nmsversion;
	}

	public ConfigFile getConfigFile() {
		return configFile;
	}
	
	
	
}
