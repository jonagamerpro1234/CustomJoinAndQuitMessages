package jss.customjoinandquitmessage.commands.utils;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String name();

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract boolean isEnabled();

    public abstract String disabledMessage();

}
