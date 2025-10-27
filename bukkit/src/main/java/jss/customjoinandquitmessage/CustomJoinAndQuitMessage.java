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
import jss.customjoinandquitmessage.storage.PlayerData;
import jss.customjoinandquitmessage.storage.database.DataBaseManager;
import jss.customjoinandquitmessage.storage.database.PlayerDataDAO;
import jss.customjoinandquitmessage.utils.update.modrinth.ModrinthUpdateChecker;
import jss.customjoinandquitmessage.utils.update.core.UpdateSettings;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

    public void onEnable() {
        instance = this;
        this.adventure = BukkitAudiences.create(this);

        metrics = new Metrics(this,6318);

        saveDefaultConfig();
        preConfigLoader.loadConfigs();
        if(!preConfigLoader.loadLangs()){
            Bukkit.getPluginManager().disablePlugins();
        }

        groupFile = new GroupFile();
        groupFile.saveDefaultConfig();

        metrics.addCustomChart( new SimplePie("using_the_group_function", () -> getConfig().getString("ChatFormat.Type","group")));

        dataBaseManager = new DataBaseManager(Settings.db_host,Settings.db_port,Settings.db_database_name,Settings.db_user,Settings.db_password);
        playerDataDAO = new PlayerDataDAO(dataBaseManager);
        cacheManager = new CacheManager();
        playerDataFile = new PlayerDataFile(getDataFolder());

        if(Settings.db_enabled){
            try {
                dataBaseManager.connect();
                dataBaseManager.createTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        groupHelper = new GroupHelper();
        groupHelper.checkPermissionPlugins(Bukkit.getPluginManager().isPluginEnabled("LuckPerms"));

        groupUpdateTask = new GroupUpdateTask(this);
        //groupUpdateTask.start(Settings.luckperms_autoUpdateGroup_delay, Settings.luckperms_autoUpdateGroup_tick,Settings.luckperms_autoUpdateGroup_enabled);

        new JoinQuitMessageHandlerFactory();

        registerListener(
                new JoinListener(),
                new QuitListener()
        );

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.register();

        //updateChecker for Modrith
        UpdateSettings settings = UpdateSettings.defaultSettings();
        new ModrinthUpdateChecker(this,"1ywOweL3",settings).checkForUpdates();
    }

    public void onDisable() {
        instance = null;

        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        metrics.shutdown();

        //groupUpdateTask.cancel();

        try {
            for (String playerName : cacheManager.getPlayerDataCache().keySet()){
                PlayerData data = cacheManager.getPlayerData(playerName);
                playerDataDAO.savePlayerData(playerName, data);
                playerDataFile.saveData(playerName,data);
            }
            dataBaseManager.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public @NotNull PluginManager getManager(){
        return  Bukkit.getPluginManager();
    }

    public static CustomJoinAndQuitMessage get(){
        return instance;
    }

}
