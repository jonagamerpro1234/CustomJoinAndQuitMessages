package jss.customjoinandquitmessage;

import jss.customjoinandquitmessage.commands.CommandHandler;
import jss.customjoinandquitmessage.files.GroupFile;
import jss.customjoinandquitmessage.files.PlayerDataFile;
import jss.customjoinandquitmessage.files.utils.PreConfigLoader;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.libs.luckperms.tasks.GroupUpdateTask;
import jss.customjoinandquitmessage.listeners.chat.JoinListener;
import jss.customjoinandquitmessage.listeners.chat.QuitListener;
import jss.customjoinandquitmessage.managers.JoinQuitMessageHandlerFactory;
import jss.customjoinandquitmessage.managers.groupmanager.GroupHelper;
import jss.customjoinandquitmessage.storage.CacheManager;
import jss.customjoinandquitmessage.storage.PlayerDataManager;
import jss.customjoinandquitmessage.storage.database.DataBaseManager;
import jss.customjoinandquitmessage.storage.database.PlayerDataDAO;
import jss.customjoinandquitmessage.storage.database.PlayerDataDAOImpl;
import jss.customjoinandquitmessage.utils.logger.Logger;
import jss.customjoinandquitmessage.utils.logger.LoggerState;
import jss.customjoinandquitmessage.utils.logger.LoggerStateManager;
import jss.customjoinandquitmessage.utils.update.github.GithubUpdateChecker;
import jss.customjoinandquitmessage.utils.update.modrinth.ModrinthUpdateChecker;
import jss.customjoinandquitmessage.utils.update.core.UpdateSettings;
import jss.customjoinandquitmessage.utils.update.spigot.SpigotUpdateChecker;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;

public final class CustomJoinAndQuitMessage extends JavaPlugin {

    private final PluginDescriptionFile jss = getDescription();
    private static CustomJoinAndQuitMessage instance;
    public final String name = jss.getName();
    public final String version = jss.getVersion();

    //public String newestVersion;
    private final PreConfigLoader preConfigLoader = new PreConfigLoader(this);
    private BukkitAudiences adventure;
    private Metrics metrics;
    public GroupFile groupFile;
    public GroupHelper groupHelper;
    public PlayerDataFile playerDataFile;
    private GroupUpdateTask groupUpdateTask;
    private DataBaseManager dataBaseManager;
    private CacheManager cacheManager;
    private PlayerDataDAO playerDataDAO;
    private PlayerDataManager playerDataManager;

    private LoggerStateManager loggerStateManager;
    private LoggerState loggerState;

    @Override
    public void onEnable() {
        instance = this;
        this.adventure = BukkitAudiences.create(this);
        loggerStateManager = new LoggerStateManager();

        try {

            loggerState = loggerStateManager.loadState();
            if (!loggerState.isShutdownCompleted()) {
                getLogger().warning("The previous logger session may not have shut down correctly.");

                if (!Logger.recoverLogFile()) {
                    getLogger().severe("Log recovery failed. The plugin will be disabled.");
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
            }
            Logger.initialize(loggerStateManager, loggerState);
            loggerState.update(false);
            loggerStateManager.saveState(loggerState);
        } catch (IOException e) {
            getLogger().severe("Failed to load or save logger state: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        metrics = new Metrics(this,6318);
        saveDefaultConfig();
        preConfigLoader.loadConfigs();
        if(!preConfigLoader.loadLangs()){
            Bukkit.getPluginManager().disablePlugins();
        }

        groupFile = new GroupFile();
        groupFile.saveDefaultConfig();

        metrics.addCustomChart( new SimplePie("using_the_group_function", () -> getConfig().getString("ChatFormat.Type","group")));


        playerDataFile = new PlayerDataFile(getDataFolder());
        cacheManager = new CacheManager();

        dataBaseManager = new DataBaseManager(
                Settings.db_host,
                Settings.db_port,
                Settings.db_database_name,
                Settings.db_user,
                Settings.db_password
        );

        playerDataDAO = new PlayerDataDAOImpl(dataBaseManager);

        if (Settings.db_enabled) {
            try {
                dataBaseManager.connect();
                dataBaseManager.createTable();
            } catch (SQLException e) {
                Logger.severe(e, "Failed to initialize the player database.");
            }
        }

        playerDataManager = new PlayerDataManager(
                playerDataFile,
                playerDataDAO,
                cacheManager,
                Settings.db_enabled
        );

        groupHelper = new GroupHelper();
        groupHelper.checkPermissionPlugins(Bukkit.getPluginManager().isPluginEnabled("LuckPerms"));
/*
        groupUpdateTask = new GroupUpdateTask(this);
        //groupUpdateTask.start(Settings.luckperms_autoUpdateGroup_delay, Settings.luckperms_autoUpdateGroup_tick,Settings.luckperms_autoUpdateGroup_enabled);
*/
        new JoinQuitMessageHandlerFactory();

        registerListener(
                new JoinListener(),
                new QuitListener()
        );

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.register();

        //updateChecker for Modrith
        new SpigotUpdateChecker(this,57006,UpdateSettings.defaultSettings()).checkForUpdates();
        new ModrinthUpdateChecker(this,"1ywOweL3",UpdateSettings.defaultSettings()).checkForUpdates();
        new GithubUpdateChecker(this,UpdateSettings.defaultSettings(),"jonagamerpro1234","CustomJoinAndQuitMessages").checkForUpdates();
    }


    @Override
    public void onDisable() {
        if (loggerStateManager != null && loggerState != null) {
            boolean logsSaved = Logger.saveLogFile();

            if (logsSaved) {
                try {
                    loggerState.update(true);
                    loggerStateManager.saveState(loggerState);
                } catch (IOException e) {
                    Logger.severe(e,"Failed to save logger state: ");
                }
            } else {
                Logger.severe("Logger shutdown was not marked as completed because log archival failed.");
            }
        }

       /* if (groupUpdateTask != null) {
            groupUpdateTask.cancel();
        }*/

        if (playerDataManager != null) {
            playerDataManager.save(PlayerDataManager.DataType.BOTH);
        }

        if (dataBaseManager != null && Settings.db_enabled) {
            try {
                dataBaseManager.disconnect();
            } catch (SQLException e) {
                Logger.severe(e, "Failed to disconnect from the player database: ");
            }
        }

        if (adventure != null) {
            adventure.close();
            adventure = null;
        }

        if (metrics != null) {
            metrics.shutdown();
        }

        instance = null;
    }


    public void reloadAllFiles(){
        reloadConfig();
        preConfigLoader.loadConfigs();
        preConfigLoader.loadLangs();
    }

    private void registerListener(Listener @NotNull ...listeners){
        for (Listener listener: listeners){
            Bukkit.getPluginManager().registerEvents(listener,this);
        }
    }

    public BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public PreConfigLoader getPreConfigLoader() {
        return preConfigLoader;
    }

    public PlayerDataDAO getPlayerDataDAO() {
        return playerDataDAO;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public @NotNull PluginManager getManager(){
        return  Bukkit.getPluginManager();
    }

    public static CustomJoinAndQuitMessage get(){
        return instance;
    }

}
