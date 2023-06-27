package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class InfoCommand extends SubCommand {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

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
        MessageUtils.sendColorMessage(sender, "&5-=-=-=-=-=[&b" + plugin.name + "&5]=-=-=-=-=-=-");
        MessageUtils.sendColorMessage(sender, "&6 ● &bAuthor: &3jonagamerpro1234");
        MessageUtils.sendColorMessage(sender, "&6 ● &eVersion: &7" + plugin.version);
        //MessageUtils.sendColorMessage(sender, "&6 ● &aLast version: &7" + plugin.getUpdateVersion());
        MessageUtils.sendColorMessage(sender, "&6 ● &9Discord: &7https://discord.gg/c5GhQDQCK5");
        MessageUtils.sendColorMessage(sender, "&5-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
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
