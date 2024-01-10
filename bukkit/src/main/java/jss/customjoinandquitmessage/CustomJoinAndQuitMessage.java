package jss.customjoinandquitmessage;

import jss.customjoinandquitmessage.commands.CommandHandler;
import jss.customjoinandquitmessage.files.GroupFile;
import jss.customjoinandquitmessage.files.utils.PreConfigLoader;
import jss.customjoinandquitmessage.listeners.chat.JoinListener;
import jss.customjoinandquitmessage.listeners.chat.QuitListener;
import jss.customjoinandquitmessage.managers.JoinQuitMessageHandlerFactory;
import jss.customjoinandquitmessage.utils.Utils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
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
    public GroupFile groupFile;

    public void onEnable() {
        instance = this;
        this.adventure = BukkitAudiences.create(this);

        metrics = new Metrics(this,6318);

        saveDefaultConfig();
        preConfigLoader.loadConfigs();
        if(!preConfigLoader.loadLangs()){
            Bukkit.getPluginManager().disablePlugins();
        }
/*
        groupFile = new GroupFile();
        groupFile.saveDefaultConfig();
*/
        metrics.addCustomChart( new SimplePie("using_the_group_function", () -> getConfig().getString("ChatFormat.Type","group")));

        new JoinQuitMessageHandlerFactory();

        registerListener(
                new JoinListener(),
                new QuitListener()
        );

         /* CommandHandler commandHandler = new CommandHandler();
        commandHandler.register();*/

        Utils.sendUpdate();
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

    public PreConfigLoader getPreConfigLoader() {
        return preConfigLoader;
    }

    public static CustomJoinAndQuitMessage get(){
        return instance;
    }

}
