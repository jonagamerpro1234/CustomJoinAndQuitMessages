package jss.customjoinandquitmessage;

import jss.customjoinandquitmessage.commands.CommandHandler;
import jss.customjoinandquitmessage.files.utils.PreConfigLoader;
import jss.customjoinandquitmessage.listeners.chat.JoinListener;
import jss.customjoinandquitmessage.listeners.chat.QuitListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class CustomJoinAndQuitMessage extends JavaPlugin {

    private final PluginDescriptionFile jss = getDescription();
    private static CustomJoinAndQuitMessage instance;
    public final String name = jss.getName();
    public final String version = jss.getVersion();
    public String newestVersion;
    private final PreConfigLoader preConfigLoader = new PreConfigLoader(this);
    private BukkitAudiences adventure;
    private Metrics metrics;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        metrics = new Metrics(this,6318);
        saveDefaultConfig();

        if(!preConfigLoader.loadLangs()){
            Bukkit.getPluginManager().disablePlugins();
        }

        registerListener(
                new JoinListener(),
                new QuitListener()
        );

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.register();
    }

    public void onDisable() {
        instance = null;
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        metrics.shutdown();
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

    public static CustomJoinAndQuitMessage get(){
        return instance;
    }
}
