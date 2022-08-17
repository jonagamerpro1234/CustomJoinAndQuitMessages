package jss.customjoinandquitmessages.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class EventUtils {

    public static @NotNull ConsoleCommandSender getStaticConsoleSender() {
        return Bukkit.getConsoleSender();
    }

}
