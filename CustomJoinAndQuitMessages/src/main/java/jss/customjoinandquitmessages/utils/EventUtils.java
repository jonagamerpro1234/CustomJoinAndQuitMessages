package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class EventUtils {

    public EventUtils(CustomJoinAndQuitMessages plugin) {
    }

    public static @NotNull ConsoleCommandSender getStaticConsoleSender() {
        return Bukkit.getConsoleSender();
    }
    public PluginManager getEventManager() {
        return Bukkit.getPluginManager();
    }

}
