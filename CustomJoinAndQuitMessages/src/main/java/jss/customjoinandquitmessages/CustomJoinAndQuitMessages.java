package jss.customjoinandquitmessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import jss.customjoinandquitmessages.commands.CustomJoinAndQuitCmd;
import jss.customjoinandquitmessages.config.ConfigFile;
import jss.customjoinandquitmessages.config.Lang;
import jss.customjoinandquitmessages.config.PlayerFile;
import jss.customjoinandquitmessages.config.PreConfigLoader;
import jss.customjoinandquitmessages.hook.HookManager;
import jss.customjoinandquitmessages.listener.InventoryListener;
import jss.customjoinandquitmessages.listener.JoinListener;
import jss.customjoinandquitmessages.manager.InventoryView;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.UpdateChecker;
import jss.customjoinandquitmessages.utils.UpdateSettings;
import jss.customjoinandquitmessages.utils.Utils;

public class CustomJoinAndQuitMessages extends JavaPlugin{

	private PluginDescriptionFile jss = getDescription();
	public String name = this.jss.getName();
	public String version = this.jss.getVersion();
	private static CustomJoinAndQuitMessages plugin;
	public Metrics metrics;
	public boolean useLatestversion;
    public boolean useLegacyversions = false;
    public String nmsversion;
	private Map<String,Lang> availableLangs = new HashMap<>();
	private EventUtils EventsUtils = new EventUtils(this);
	private ConfigFile configFile = new ConfigFile(this, "config.yml");
	private HookManager hooksManager = new HookManager(this);
	private PreConfigLoader preConfigLoader = new PreConfigLoader(this);
	private String updateVersion;
	private boolean useLegacyConfig = false;
	private ArrayList<InventoryView> inventoryViews;
	private PlayerFile playerFile = new PlayerFile(this, "players.yml");
	
	public void onLoad() {
		Utils.sendLoadTitle(version);
		inventoryViews = new ArrayList<>();
	}
	
	public void onEnable() {
		plugin = this;
		Utils.setEnabled(version);
		configFile.saveDefaultConfig();
		configFile.create();		
		preConfigLoader.loadConfig();
		if(!preConfigLoader.loadLangs()) {
			Logger.error("&cError load lang files");
			Logger.warning("&eDisable plugin for error");
			Bukkit.getPluginManager().disablePlugins();
			return;
		}
		
		int cfgv = getConfigFile().getConfig().getInt("Config.Config-Version");
		if(cfgv > 2) {
			useLegacyConfig = true;
		}
		
		if(useLegacyConfig) {
			Logger.warning("&eYour config.yml -> [v1] is out of date, update config.yml -> [v2]");
		}
		
		
		
		metrics = new Metrics(this);
        nmsversion = Bukkit.getServer().getClass().getPackage().getName();
        nmsversion = nmsversion.substring(nmsversion.lastIndexOf(".") + 1);
        
        if (nmsversion.equalsIgnoreCase("v1_8_R1") || nmsversion.equalsIgnoreCase("v1_7_")) { 
        	useLegacyversions = true;
        	if(useLegacyversions) {
        		Utils.sendColorMessage(EventsUtils.getConsoleSender(), " is legacy &e1.7_? &8|&e 1.8_R1");
        	}
        }
        
        if (nmsversion.equalsIgnoreCase("v1_8_R3")) { 
        	useLegacyversions = true;
        	if(useLegacyversions) {
        		Utils.sendColorMessage(EventsUtils.getConsoleSender(), Utils.getPrefix() + " " + "&7Use " + nmsversion + " &cdisabled &7method &b1.16");
        	}
        }else if(nmsversion.equalsIgnoreCase("v1_16_R1") || nmsversion.equalsIgnoreCase("v1_16_R2") || nmsversion.equalsIgnoreCase("v1_16_R3")){
        	Utils.sendColorMessage(EventsUtils.getConsoleSender(), Utils.getPrefix() + "&7Use " + nmsversion + " &aenabled &7method &b1.16 ");
        }else if(nmsversion.equalsIgnoreCase("v1_17_R1")) {
        	Utils.sendColorMessage(EventsUtils.getConsoleSender(), Utils.getPrefix() + "&7Use " + nmsversion + " &aenabled &7method &b1.17 ");
        }else if(nmsversion.equalsIgnoreCase("v1_18_R1")) {
        	Utils.sendColorMessage(EventsUtils.getConsoleSender(), Utils.getPrefix() + "&7Use " + nmsversion + " &aenabled &7method &b1.18 ");
        }
		setupCommands();
		setupEvents();
		hooksManager.load();
		
		new UpdateChecker(this, UpdateSettings.ID).getUpdateVersion(version -> {
			
			updateVersion = version;
			
			if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
				Logger.success("&a" + this.name + " is up to date!");
			}else {
				Logger.outLine("&5<||" + Utils.setLine("&5"));
				Logger.warning("&5<||&b" + this.name + " is outdated!");
				Logger.warning("&5<||&bNewest version: &a" + version);
				Logger.warning("&5<||&bYour version: &d" + UpdateSettings.VERSION);
				Logger.warning("&5<||&bUpdate Here on Spigot: &e" + UpdateSettings.URL_PlUGIN[0]);
				Logger.outLine("&5<||" + Utils.setLine("&5"));
			}
		});
	}
	
	public void onDisable() {
		Utils.setDisabled(version);
		metrics = null;
	}
	
	public void setupCommands() {
		new CustomJoinAndQuitCmd(this);
	}
	
	public void setupEvents() {
		new JoinListener(this);
		new InventoryListener(this);
	}
	
	public void reloadAllFiles() {
		getConfigFile().reloadConfig();
		preConfigLoader.loadConfig();
		preConfigLoader.loadLangs();
	}
	
	public static CustomJoinAndQuitMessages getInstance() {
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
	
	public PlayerFile getPlayerFile() {
		return playerFile;
	}
	
	public PreConfigLoader getPreConfigLoader() {
		return preConfigLoader;
	}
	
	public String getUpdateVersion() {
		return updateVersion;
	}
	
	public void registerInventory(Player player, String inventoryName) {
		if(getInventoryView(player) == null) {
			inventoryViews.add(new InventoryView(player, inventoryName));
		}
	}
	
	public void unregisterInventory(Player player) {
		for(int i = 0; i < inventoryViews.size(); i++) {
			if(((InventoryView)inventoryViews.get(i)).getPlayer().getName().equals(player.getName())) {
				inventoryViews.remove(i);
			}
		}
	}
	
	public InventoryView getInventoryView(Player player) {
		for(int i = 0; i < inventoryViews.size(); i++) {
			if(((InventoryView) inventoryViews.get(i)).getPlayer().getName().equals(player.getName())) {
				return (InventoryView) inventoryViews.get(i);
			}
		}
		return null;
	}
}
