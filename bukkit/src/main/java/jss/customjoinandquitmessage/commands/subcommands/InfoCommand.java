package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import org.bukkit.command.CommandSender;

public class InfoCommand extends SubCommand {

    public String name() {
        return "info";
    }

    public String permission() {
        return "";
    }

    public boolean requiresPermission() {
        return false;
    }

    public boolean onCommand(CommandSender sender, String[] args) {

        return true;
    }

    public boolean allowConsole() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public String disabledMessage() {
        return Settings.lang_disableCommand;
    }
}
