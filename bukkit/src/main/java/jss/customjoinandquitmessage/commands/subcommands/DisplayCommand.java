package jss.customjoinandquitmessage.commands.subcommands;

import jss.customjoinandquitmessage.commands.utils.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class DisplayCommand extends SubCommand {

    public String name() {
        return "display";
    }

    @Override
    public String permission() {
        return null;
    }

    public boolean requiresPermission() {
        return true;
    }

    public void onCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){

        }
    }

    public boolean allowConsole() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public String disabledMessage() {
        return "";
    }
}
