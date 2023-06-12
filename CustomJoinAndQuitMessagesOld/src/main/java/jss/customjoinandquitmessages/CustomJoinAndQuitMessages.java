package jss.customjoinandquitmessages;

import jss.customjoinandquitmessages.commands.CustomJoinAndQuitCmd;
import jss.customjoinandquitmessages.config.ConfigFile;
import jss.customjoinandquitmessages.config.GroupsFile;
import jss.customjoinandquitmessages.config.Lang;
import jss.customjoinandquitmessages.config.PlayerFile;
import jss.customjoinandquitmessages.config.utils.PreConfigLoader;
import jss.customjoinandquitmessages.listener.chat.JoinListener;
import jss.customjoinandquitmessages.listener.TaskLoader;
import jss.customjoinandquitmessages.listener.chat.QuitListener;
import jss.customjoinandquitmessages.manager.HookManager;
import jss.customjoinandquitmessages.utils.InventoryView;
import jss.customjoinandquitmessages.update.UpdateChecker;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomJoinAndQuitMessages extends JavaPlugin {

    private static CustomJoinAndQuitMessages plugin;
    public Metrics metrics;
    private final PluginDescriptionFile jss = getDescription();
    public String name = this.jss.getName();
    public String version = this.jss.getVersion();
    private Map<String, Lang> availableLangs = new HashMap<>();
    private final ConfigFile configFile = new ConfigFile(this, "config.yml");
    private final HookManager hooksManager = new HookManager();
    private final PreConfigLoader preConfigLoader = new PreConfigLoader(this);
    @SuppressWarnings("unused")
    private String updateVersion;
    private boolean useLegacyConfig = false;
    private ArrayList<InventoryView> inventoryViews;
    private final PlayerFile playerFile = new PlayerFile(this, "players.yml");
    private final GroupsFile groupsFile = new GroupsFile(this, "groups.yml");
    private BukkitAudiences adventure;

    public void onLoad() {
        Util.sendLoadTitle(version);
        inventoryViews = new ArrayList<>();

        File logDirectory = new File(getDataFolder(), "logs");
        if (!logDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            logDirectory.mkdirs();
        }
    }

    public void onEnable() {
        metrics = new Metrics(this,6318);
        plugin = this;
        this.adventure = BukkitAudiences.create(this);

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
        if (!cfg.equals("4") || old_cfg) {
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

        UpdateChecker updateChecker = new UpdateChecker(this);
        updateChecker.sendSpigotUpdate();
    }

    public void onDisable() {
        plugin = null;
        metrics = null;

        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        getServer().getScheduler().cancelTasks(this);
        Util.setDisabled(version);
    }

    public void setupCommands() {
        new CustomJoinAndQuitCmd(this);
    }

    public void setupEvents() {
        new JoinListener();
        new QuitListener();
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

    public BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public Lang Locale() {
        return availableLangs.get(Settings.settings_defaultLanguage);
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
