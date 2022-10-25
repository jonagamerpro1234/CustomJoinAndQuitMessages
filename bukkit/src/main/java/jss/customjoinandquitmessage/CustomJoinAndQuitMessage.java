package jss.customjoinandquitmessage;

import jss.customjoinandquitmessage.commands.CommandHandler;
import jss.customjoinandquitmessage.files.utils.PreConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomJoinAndQuitMessage extends JavaPlugin {

    private final PluginDescriptionFile jss = getDescription();
    private static CustomJoinAndQuitMessage instance;
    public final String version = jss.getVersion();
    public String newestVersion;
    private final PreConfigLoader preConfigLoader = new PreConfigLoader();

    public void onEnable() {
        instance = this;

        if(!preConfigLoader.loadLangs()){
            Bukkit.getPluginManager().disablePlugins();
        }

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.register();

    }

    public void onDisable() {
        instance = null;
    }

    public void reloadAllFiles(){

    }


    public static CustomJoinAndQuitMessage get(){
        return instance;
    }
}
