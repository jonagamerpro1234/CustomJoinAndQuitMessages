package jss.customjoinandquitmessages.commands.subcommands;

import jss.commandapi.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {
    public String name() {
        return "help";
    }

    public String permission() {
        return "";
    }

    public boolean requiresPermission() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }

    public String disabledMessage() {
        return super.disabledMessage();
    }

    public boolean onCommand(CommandSender commandSender, String[] strings) {
        return false;
    }
}
