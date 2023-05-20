package jss.customjoinandquitmessage.commands.subcommands;

import jss.customjoinandquitmessage.commands.utils.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    public String name() {
        return "reload";
    }

    @Override
    public String permission() {
        return null;
    }

    public boolean requiresPermission() {
        return true;
    }

    public void onCommand(CommandSender sender, String[] args) {

    }

    public boolean allowConsole() {
        return true;
    }

    public boolean isEnabled() {
        return false;
    }

    public String disabledMessage() {
        return null;
    }
}