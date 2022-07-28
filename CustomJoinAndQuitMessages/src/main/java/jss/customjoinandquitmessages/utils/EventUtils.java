package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventUtils {

    private CustomJoinAndQuitMessages plugin;

    public EventUtils(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    public static ConsoleCommandSender getStaticConsoleSender() {
        return Bukkit.getConsoleSender();
    }

    public void initEvent(Listener... listeners) {
        for (Listener listener : listeners) {
            getEventManager().registerEvents(listener, plugin);
        }
    }

    public PluginManager getEventManager() {
        return Bukkit.getPluginManager();
    }

    public ConsoleCommandSender getConsoleSender() {
        return Bukkit.getConsoleSender();
    }
}
