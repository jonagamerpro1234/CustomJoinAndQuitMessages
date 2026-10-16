package jss.customjoinandquitmessage.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

//temp class
public class EventUtils {

    public static @NotNull ConsoleCommandSender getConsoleSender() {
        return Bukkit.getConsoleSender();
    }

}
