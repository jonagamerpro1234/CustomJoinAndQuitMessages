package jss.customjoinandquitmessage;

import jss.customjoinandquitmessage.commands.CommandHandler;
import jss.customjoinandquitmessage.files.utils.PreConfigLoader;
import jss.customjoinandquitmessage.listeners.chat.JoinListener;
import jss.customjoinandquitmessage.listeners.chat.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class CustomJoinAndQuitMessage extends JavaPlugin {

    private final PluginDescriptionFile jss = getDescription();
    private static CustomJoinAndQuitMessage instance;
    public final String version = jss.getVersion();
    public String newestVersion;
    private final PreConfigLoader preConfigLoader = new PreConfigLoader(this);

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {

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
    }

    public void reloadAllFiles(){

    }

    private void registerListener(Listener @NotNull ...listeners){
        for (Listener listener: listeners){
            Bukkit.getPluginManager().registerEvents(listener,this);
        }
    }

    public static CustomJoinAndQuitMessage get(){
        return instance;
    }
}
