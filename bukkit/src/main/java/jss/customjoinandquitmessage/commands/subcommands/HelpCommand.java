package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    public String name() {
        return "help";
    }

    public String permission()                                                                                                                                                                                         {
        return "command.help";
    }

    public boolean requiresPermission() {
        return true;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        for (String s : Settings.lang_helpCommand) {
            MessageUtils.sendColorMessage(sender, s);
        }
        return true;
    }

    public boolean allowConsole() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public String disabledMessage() {
        return null;
    }
}
