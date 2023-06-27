package jss.customjoinandquitmessage.commands.subcommands;

import jss.commandapi.SubCommand;
import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.Utils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends SubCommand {

    private static final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    public String name() {
        return "reload";
    }

    public String permission() {
        return "command.reload";
    }

    public boolean requiresPermission() {
        return true;
    }

    public boolean onCommand(CommandSender sender, String @NotNull [] args) {
        if (args.length >= 2){

            if(args[1].equalsIgnoreCase("config")){
                Utils.sendColorMessage(sender, Settings.lang_reloadCommand);
                return true;
            }

            if (args[1].equalsIgnoreCase("lang")){
                Utils.sendColorMessage(sender, Settings.lang_reloadCommand);
                return true;
            }

            Utils.sendColorMessage(sender, Settings.lang_unknownArguments);
            return true;
        }

        plugin.reloadAllFiles();
        Utils.sendColorMessage(sender, Settings.lang_reloadCommand);
        return true;
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