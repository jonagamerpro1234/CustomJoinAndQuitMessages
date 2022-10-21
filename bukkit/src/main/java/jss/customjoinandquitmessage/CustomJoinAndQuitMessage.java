package jss.customjoinandquitmessage;

import jss.customjoinandquitmessage.commands.CommandHandler;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomJoinAndQuitMessage extends JavaPlugin {

    private final PluginDescriptionFile jss = getDescription();
    private static CustomJoinAndQuitMessage instance;
    public final String version = jss.getVersion();


    @Override
    public void onEnable() {
        instance = this;
        CommandHandler commandHandler = new CommandHandler();

        commandHandler.register();

    }

    @Override
    public void onDisable() {
        instance = null;
    }


    public static CustomJoinAndQuitMessage get(){
        return instance;
    }
}
