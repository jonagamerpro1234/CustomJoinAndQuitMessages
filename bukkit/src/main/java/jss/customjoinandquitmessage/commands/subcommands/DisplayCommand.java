package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class DisplayCommand extends SubCommand {

    public String name() {
        return "display";
    }

    public String permission() {
        return "command.display";
    }

    public boolean requiresPermission() {
        return true;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        return true;
    }

    public boolean allowConsole() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public String disabledMessage() {
        return Settings.lang_disableCommand;
    }
}
