package jss.customjoinandquitmessage.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class EventUtils {

    public static @NotNull ConsoleCommandSender getConsoleSender() {
        return Bukkit.getConsoleSender();
    }

}
