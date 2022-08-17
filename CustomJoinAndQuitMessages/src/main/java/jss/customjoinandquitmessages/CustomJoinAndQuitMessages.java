package jss.customjoinandquitmessages;

import jss.customjoinandquitmessages.commands.CustomJoinAndQuitCmd;
import jss.customjoinandquitmessages.config.*;
import jss.customjoinandquitmessages.hook.HookManager;
import jss.customjoinandquitmessages.listener.JoinListener;
import jss.customjoinandquitmessages.listener.TaskLoader;
import jss.customjoinandquitmessages.manager.InventoryView;
import jss.customjoinandquitmessages.utils.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomJoinAndQuitMessages extends JavaPlugin {

    private static CustomJoinAndQuitMessages plugin;
    public Metrics metrics;
    public boolean useLatestversion;
    private final PluginDescriptionFile jss = getDescription();
    public String name = this.jss.getName();
    public String version = this.jss.getVersion();
    private Map<String, Lang> availableLangs = new HashMap<>();
    private final ConfigFile configFile = new ConfigFile(this, "config.yml");
    private final HookManager hooksManager = new HookManager(this);
    private final PreConfigLoader preConfigLoader = new PreConfigLoader(this);
    private String updateVersion;
    private boolean useLegacyConfig = false;
    private ArrayList<InventoryView> inventoryViews;
    private final PlayerFile playerFile = new PlayerFile(this, "players.yml");
    private final GroupsFile groupsFile = new GroupsFile(this, "groups.yml");

    public void onLoad() {
        Util.sendLoadTitle(version);
        inventoryViews = new ArrayList<>();
    }

    public void onEnable() {
        metrics = new Metrics(this,6318);
        plugin = this;
        Util.setEnabled(version);

        configFile.saveDefaultConfig();
        configFile.create();
        preConfigLoader.loadConfig();

        if (!preConfigLoader.loadLangs()) {
            Logger.error("&cload lang files");
            Logger.warning("&eDisable plugin for error");
            Bukkit.getPluginManager().disablePlugins();
            return;
        }

        String cfg = getConfigFile().getConfig().getString("File-Version");
        boolean old_cfg = getConfigFile().getConfig().contains("Config.Config-Version");
        assert cfg != null;
        if (!cfg.equals("3") || old_cfg) {
            useLegacyConfig = true;
        }

        if (useLegacyConfig) {
            Logger.warning("&eYour config is outdated please update it to avoid possible bugs");
        }

        groupsFile.saveDefaultConfig();
        groupsFile.create();
        hooksManager.load();
        setupEvents();
        setupCommands();

        new UpdateChecker(this, UpdateSettings.ID).getUpdateVersion(version -> {
            updateVersion = version;
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Logger.success("&a" + this.name + " is up to date!");
            } else {
                Logger.outLine("&5<||" + Util.setLine("&5"));
                Logger.warning("&5<||&b" + this.name + " is outdated!");
                Logger.warning("&5<||&bNewest version: &a" + version);
                Logger.warning("&5<||&bYour version: &d" + UpdateSettings.VERSION);
                Logger.warning("&5<||&bUpdate Here on Spigot: &e" + UpdateSettings.URL_PlUGIN[0]);
                Logger.outLine("&5<||" + Util.setLine("&5"));
            }
        });
    }

    public void onDisable() {
        plugin = null;
        metrics = null;
        getServer().getScheduler().cancelTasks(this);
        Util.setDisabled(version);
    }

    public void setupCommands() {
        new CustomJoinAndQuitCmd(this);
    }

    public void setupEvents() {
        new JoinListener();
        TaskLoader taskLoader = new TaskLoader(this);
        taskLoader.onUpdateGroup();
    }

    public void reloadAllFiles() {
        getConfigFile().reloadConfig();
        getGroupsFile().reloadConfig();
        getPlayerFile().reloadConfig();
        preConfigLoader.loadConfig();
        preConfigLoader.loadLangs();
    }

    public Lang Locale() {
        return availableLangs.get(Settings.defaultLanguage);
    }

    public void setAvailableLocales(HashMap<String, Lang> availableLangs) {
        this.availableLangs = availableLangs;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public PlayerFile getPlayerFile() {
        return playerFile;
    }

    public GroupsFile getGroupsFile() {
        return groupsFile;
    }


    public String getUpdateVersion() {
        return updateVersion;
    }

    public void registerInventory(Player player, String inventoryName) {
        if (getInventoryView(player) == null) {
            inventoryViews.add(new InventoryView(player, inventoryName));
        }
    }

    @SuppressWarnings({"SuspiciousListRemoveInLoop", "unused"})
    public void unregisterInventory(Player player) {
        for (int i = 0; i < inventoryViews.size(); i++) {
            if (inventoryViews.get(i).getPlayer().getName().equals(player.getName())) {
                inventoryViews.remove(i);
            }
        }
    }

    public InventoryView getInventoryView(Player player) {
        for (InventoryView inventoryView : inventoryViews) {
            if (inventoryView.getPlayer().getName().equals(player.getName())) {
                return inventoryView;
            }
        }
        return null;
    }

    public static CustomJoinAndQuitMessages get() {
        return plugin;
    }

}
