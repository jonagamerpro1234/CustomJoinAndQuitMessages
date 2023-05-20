package jss.customjoinandquitmessage.commands.subcommands;

import jss.customjoinandquitmessage.commands.utils.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import org.bukkit.command.CommandSender;

public class InfoCommand extends SubCommand {

    public String name() {
        return "info";
    }

    @Override
    public String permission() {
        return null;
    }

    public boolean requiresPermission() {
        return false;
    }

    public void onCommand(CommandSender sender, String[] args) {

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
