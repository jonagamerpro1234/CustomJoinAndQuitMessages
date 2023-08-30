package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    private CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

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
        MessageUtils.sendColorMessage(sender, " ");
        MessageUtils.sendColorMessage(sender, "&5-=-=-=-=-=-=-=-=-=&6[&d" + plugin.name + "&6]&5=-=-=-=-=-=-=-=-=-");
        for (String s : Settings.lang_helpCommand) {
            MessageUtils.sendColorMessage(sender, s);
        }
        MessageUtils.sendColorMessage(sender, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        MessageUtils.sendColorMessage(sender, " ");
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
